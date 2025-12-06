import { createServerTokenAuthentication } from 'alova/client'
import { createAlova } from 'alova'
import VueHook from 'alova/vue'
import adapterFetch from 'alova/fetch'
import { useAuthStore } from '@/stores'
import type { VueHookType } from 'alova/vue'
import {
  handleAuthError,
  handleBusinessError,
  handleMethodError,
  handleResponseError,
  handleResult
} from './handler'
import { defaultAlovaConfig, defaultAuthConfig } from './config'

const { onAuthRequired, onResponseRefreshToken } = createServerTokenAuthentication<VueHookType>({
  refreshTokenOnSuccess: {
    isExpired: async (response, method) => {
      const apiData = await response.clone().json()
      const isExpired = method.meta && method.meta.isExpired
      return (
        (defaultAuthConfig.expiredCodes.includes(response.status) ||
          defaultAuthConfig.expiredCodes.includes(apiData.code)) &&
        !isExpired
      )
    },

    handler: async (response, method) => {
      if (!method.meta) {
        method.meta = {
          isExpired: true
        }
      } else {
        method.meta.isExpired = true
      }

      const apiData = await response.clone().json()
      handleAuthError(response, method, apiData)
    }
  },

  assignToken: method => {
    const authStore = useAuthStore()
    const token = authStore.getToken
    if (token) {
      method.config.headers[defaultAuthConfig.tokenHeader] =
        `${defaultAuthConfig.tokenPrefix} ${token}`
    }
  }
})

const alovaInstance = createAlova({
  statesHook: VueHook,
  requestAdapter: adapterFetch(),
  cacheFor: null,
  baseURL: defaultAlovaConfig.baseURL,
  timeout: defaultAlovaConfig.timeout,

  beforeRequest: onAuthRequired(method => {
    if (method.meta?.isFormPost) {
      method.config.headers['Content-Type'] = 'application/x-www-form-urlencoded'
      method.data = new URLSearchParams(method.data as URLSearchParams).toString()
    }
  }),

  responded: onResponseRefreshToken({
    onSuccess: async (response, method) => {
      const { status } = response

      const apiData = await response.clone().json()

      // 正常请求
      if (status === 200) {
        if (method.meta?.isBlob) {
          return response.blob()
        }

        // 业务请求成功
        if (apiData.code === 200) {
          return handleResult(apiData)
        }

        // 业务 401
        if (apiData.code === 401) {
          return handleAuthError(response, method, apiData)
        }

        // 业务请求失败
        return handleBusinessError(apiData)
      }

      // 请求 401 不做处理
      if (status === 401) {
        return handleResult({})
      }

      // 请求失败
      return handleResponseError(apiData)
    },

    onError: async (error, method) => {
      handleMethodError(error, method)
    },

    onComplete: async _method => {}
  })
})

export const request = alovaInstance
