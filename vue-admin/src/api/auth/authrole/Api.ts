import { request } from '@/utils'

/**
 * 角色 API请求
 */
export function useAuthRoleApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 角色 分页接口
     */
    PageAuthRole(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/role/page`, {
        params: {
          ...data
        }
      })
    },

    /*
     * 角色 新增接口
     */
    AddAuthRole(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/auths/role/add`, data)
    },

    /*
     * 角色 修改接口
     */
    EditAuthRole(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/auths/role/edit`, data)
    },

    /*
     * 角色 删除接口
     */
    DeleteAuthRole(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/auths/role/delete`, ids)
    },

    /*
     * 角色 详情接口
     */
    GetAuthRole(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/role/detail/${id}`)
    },

    /*
     * 角色 最新接口
     */
    LatestAuthRole(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/role/latest`, {
        params: {
          n
        }
      })
    },

    /*
     * 角色 TopN接口
     */
    TopAuthRole(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/role/top`, {
        params: {
          n
        }
      })
    }
  }
}
