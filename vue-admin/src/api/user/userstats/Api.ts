import { request } from '@/utils'

/**
 * 用户统计信息 API请求
 */
export function useUserStatsApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 用户统计信息 分页接口
     */
    PageUserStats(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/users/stats/page`, {
        params: {
          ...data
        }
      })
    },

    /*
     * 用户统计信息 新增接口
     */
    AddUserStats(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/users/stats/add`, data)
    },

    /*
     * 用户统计信息 修改接口
     */
    EditUserStats(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/users/stats/edit`, data)
    },

    /*
     * 用户统计信息 删除接口
     */
    DeleteUserStats(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/users/stats/delete`, ids)
    },

    /*
     * 用户统计信息 详情接口
     */
    GetUserStats(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/users/stats/detail/${id}`)
    },

    /*
     * 用户统计信息 最新接口
     */
    LatestUserStats(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/users/stats/latest`, {
        params: {
          n
        }
      })
    },

    /*
     * 用户统计信息 TopN接口
     */
    TopUserStats(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/users/stats/top`, {
        params: {
          n
        }
      })
    }
  }
}
