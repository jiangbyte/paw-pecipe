/**
 * 请求配置类型
 */
interface RequestConfig {
  baseURL: string
  timeout: number
}

/**
 * 认证配置类型
 */
interface AuthConfig {
  tokenHeader: string
  tokenPrefix: string
  expiredCodes: number[]
}

/**
 * 后端返回结构类型
 */
interface BackEndResponseStructure {
  code: number
  message: string
  data: any
  succsess: boolean
  time: string
}

// 默认配置
export const defaultAlovaConfig: RequestConfig = {
  baseURL: import.meta.env.VITE_API_URL || '',
  timeout: 60 * 1000
}

// 认证配置
export const defaultAuthConfig: AuthConfig = {
  tokenHeader: 'Authorization',
  tokenPrefix: 'Bearer',
  expiredCodes: [401]
}

// 后端返回结构
export const defaultBackEndResponseStructure: BackEndResponseStructure = {
  code: 0,
  message: '',
  data: null,
  succsess: true,
  time: ''
}
