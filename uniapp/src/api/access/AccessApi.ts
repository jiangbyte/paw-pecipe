import { request } from '@/utils'

export function useAccessApi() {
  const pathPrefix = `/api/v1`
  return {
    Captcha() {
      return request.Get<IResult<any>>(`${pathPrefix}/access/captcha`)
    },
    DoLogin(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/access/login`, data)
    },
    DoLogout() {
      return request.Post<IResult<any>>(`${pathPrefix}/access/logout`)
    },
    DoRegister(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/access/register`, data)
    },
    // 密码相关
    DoResetPassword(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/access/password/reset`, data)
    },
    DoValidateResetPasswordToken(token: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/access/password/reset/token/validate`, {
        params: {
          token,
        },
      })
    },
    DoPasswordResetConfirm(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/access/password/reset/confirm`, data)
    },
    ChangePassword(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/access/password/change`, data)
    },
  }
}
