import { request } from '@/utils'

/**
 * 用户组 API请求
 */
export function useAuthGroupApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 用户组 分页接口
     */
    PageAuthGroup(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/group/page`, {
        params: {
          ...data
        }
      })
    },

    /*
     * 用户组 新增接口
     */
    AddAuthGroup(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/auths/group/add`, data)
    },

    /*
     * 用户组 修改接口
     */
    EditAuthGroup(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/auths/group/edit`, data)
    },

    /*
     * 用户组 删除接口
     */
    DeleteAuthGroup(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/auths/group/delete`, ids)
    },

    /*
     * 用户组 详情接口
     */
    GetAuthGroup(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/group/detail/${id}`)
    },

    /*
     * 用户组 最新接口
     */
    LatestAuthGroup(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/group/latest`, {
        params: {
          n
        }
      })
    },

    /*
     * 用户组 TopN接口
     */
    TopAuthGroup(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/group/top`, {
        params: {
          n
        }
      })
    }
  }
}
