import { request } from '@/utils'

/**
 * 核心账户 API请求
 */
export function useAuthAccountApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 核心账户 分页接口
     */
    PageAuthAccount(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/account/page`, {
        params: {
          ...data
        }
      })
    },

    /*
     * 核心账户 新增接口
     */
    AddAuthAccount(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/auths/account/add`, data)
    },

    /*
     * 核心账户 修改接口
     */
    EditAuthAccount(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/auths/account/edit`, data)
    },

    /*
     * 核心账户 删除接口
     */
    DeleteAuthAccount(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/auths/account/delete`, ids)
    },

    /*
     * 核心账户 详情接口
     */
    GetAuthAccount(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/account/detail/${id}`)
    },

    /*
     * 核心账户 最新接口
     */
    LatestAuthAccount(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/account/latest`, {
        params: {
          n
        }
      })
    },

    /*
     * 核心账户 TopN接口
     */
    TopAuthAccount(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/account/top`, {
        params: {
          n
        }
      })
    },
    GetUserAccount(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/account/user/${id}`)
    }
  }
}
