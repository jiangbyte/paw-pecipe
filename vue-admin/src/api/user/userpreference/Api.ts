import { request } from '@/utils'

/**
 * 用户偏好设置 API请求
 */
export function useUserPreferenceApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 用户偏好设置 分页接口
     */
    PageUserPreference(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/users/preference/page`, {
        params: {
          ...data
        }
      })
    },

    /*
     * 用户偏好设置 新增接口
     */
    AddUserPreference(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/users/preference/add`, data)
    },

    /*
     * 用户偏好设置 修改接口
     */
    EditUserPreference(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/users/preference/edit`, data)
    },

    /*
     * 用户偏好设置 删除接口
     */
    DeleteUserPreference(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/users/preference/delete`, ids)
    },

    /*
     * 用户偏好设置 详情接口
     */
    GetUserPreference(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/users/preference/detail/${id}`)
    },

    /*
     * 用户偏好设置 最新接口
     */
    LatestUserPreference(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/users/preference/latest`, {
        params: {
          n
        }
      })
    },

    /*
     * 用户偏好设置 TopN接口
     */
    TopUserPreference(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/users/preference/top`, {
        params: {
          n
        }
      })
    }
  }
}
