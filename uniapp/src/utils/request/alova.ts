import { createAlova } from 'alova'
import AdapterUniapp from '@alova/adapter-uniapp'

import { defaultAlovaConfig } from './config'

const alovaInstance = createAlova({
  cacheFor: null,
  baseURL: defaultAlovaConfig.baseURL,
  timeout: defaultAlovaConfig.timeout,
  ...AdapterUniapp(),

  beforeRequest: (method) => {
    if (method.meta?.isFormPost) {
      method.config.headers['Content-Type'] = 'application/x-www-form-urlencoded'
      method.data = new URLSearchParams(method.data as URLSearchParams).toString()
    }
  },

  responded(response) {
    const { statusCode, data } = response as UniNamespace.RequestSuccessCallbackResult
    console.log(statusCode)
    // if (statusCode >= 400) {
    //   throw new Error('请求错误')
    // }
    if (statusCode === 200) {
      // 业务200
      if (data?.code === 200) {
        return data
      }
    }
    else {
      uni.showToast({
        title: data?.message || '请求错误',
        icon: 'error',
      })
    }

    return data || null
  },

})

export const request = alovaInstance
