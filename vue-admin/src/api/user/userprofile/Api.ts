import { request } from '@/utils'

/**
 * 用户档案详情 API请求
 */
export function useUserProfileApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 用户档案详情 分页接口
     */
    PageUserProfile(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/users/profile/page`, {
        params: {
          ...data
        }
      })
    },

    /*
     * 用户档案详情 新增接口
     */
    AddUserProfile(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/users/profile/add`, data)
    },

    /*
     * 用户档案详情 修改接口
     */
    EditUserProfile(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/users/profile/edit`, data)
    },

    /*
     * 用户档案详情 删除接口
     */
    DeleteUserProfile(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/users/profile/delete`, ids)
    },

    /*
     * 用户档案详情 详情接口
     */
    GetUserProfile(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/users/profile/detail/${id}`)
    },

    /*
     * 用户档案详情 最新接口
     */
    LatestUserProfile(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/users/profile/latest`, {
        params: {
          n
        }
      })
    },

    /*
     * 用户档案详情 TopN接口
     */
    TopUserProfile(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/users/profile/top`, {
        params: {
          n
        }
      })
    }
  }
}
