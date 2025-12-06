// import { useAuthStore } from '@/stores'
import { defaultBackEndResponseStructure } from './config'

// import { MessagePlugin } from 'tdesign-vue-next'

function handleResult(apiData: Record<string, any>) {
  apiData = { ...defaultBackEndResponseStructure, ...apiData }

  return apiData
}

function handleResponseError(apiData: Record<string, any>) {
  apiData = { ...defaultBackEndResponseStructure, ...apiData }

  if (apiData.message) {
    // window.$message.warning(`${apiData.message} (${apiData.code})`)
    // MessagePlugin.warning(`${apiData.message} (${apiData.code})`)
  }

  return apiData
}
function handleBusinessError(apiData: Record<string, any>) {
  apiData = { ...defaultBackEndResponseStructure, ...apiData }

  if (apiData.message) {
    // window.$message.warning(`${apiData.message} (${apiData.code})`)
    // MessagePlugin.warning(`${apiData.message} (${apiData.code})`)
  }

  return apiData
}

function handleMethodError(error: any, method: any) {
  const tip = `[${method.type}] - [${method.url}] - ${error.message}`
  // window.$message.error(tip)
  // MessagePlugin.warning(tip)
  console.log(tip)
}

function handleAuthError(response: any, method: any, apiData?: Record<string, any>) {
  // const authStore = useAuthStore()

  // 区分认证401和业务401
  if (response.status === 401) {
    // HTTP 401 - 认证过期
    console.warn(`[认证过期] ${method.type} ${method.url}`)

    // 检查是否是重复触发（避免多次弹出提示）
    if (!method.meta?.authErrorHandled) {
      if (apiData?.message) {
        // window.$message.warning(apiData.message)
        // MessagePlugin.warning(apiData.message)
      }

      // 标记已处理，避免重复触发
      if (!method.meta) {
        method.meta = {}
      }
      method.meta.authErrorHandled = true

      // 清除token
      // authStore.resetAuth()
    }
  }
  else if (apiData?.code === 401) {
    // 业务 401 - 业务层面的权限错误
    console.warn(`[业务权限错误] ${method.type} ${method.url}`, apiData)

    // 显示业务错误信息
    if (apiData.message) {
      // window.$message.warning(apiData.message)
      // MessagePlugin.warning(apiData.message)
    }
    else {
      // window.$message.warning('无权限访问该资源')
      // MessagePlugin.warning('无权限访问该资源')
    }
  }
}

export {
  handleAuthError,
  handleBusinessError,
  handleMethodError,
  handleResponseError,
  handleResult,
}
