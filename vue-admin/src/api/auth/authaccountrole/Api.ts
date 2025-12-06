import { request } from '@/utils'

/**
 * 账户角色关联 API请求
 */
export function useAuthAccountRoleApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 账户角色关联 分页接口
     */
    PageAuthAccountRole(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/account/role/page`, {
        params: {
          ...data
        }
      })
    },

    /*
     * 账户角色关联 新增接口
     */
    AddAuthAccountRole(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/auths/account/role/add`, data)
    },

    /*
     * 账户角色关联 修改接口
     */
    EditAuthAccountRole(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/auths/account/role/edit`, data)
    },

    /*
     * 账户角色关联 删除接口
     */
    DeleteAuthAccountRole(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/auths/account/role/delete`, ids)
    },

    /*
     * 账户角色关联 详情接口
     */
    GetAuthAccountRole(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/account/role/detail/${id}`)
    },

    /*
     * 账户角色关联 最新接口
     */
    LatestAuthAccountRole(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/account/role/latest`, {
        params: {
          n
        }
      })
    },

    /*
     * 账户角色关联 TopN接口
     */
    TopAuthAccountRole(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/account/role/top`, {
        params: {
          n
        }
      })
    }
  }
}
