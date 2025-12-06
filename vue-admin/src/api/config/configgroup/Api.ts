import { request } from '@/utils'

/**
 * 配置分组 API请求
 */
export function useConfigGroupApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 配置分组 分页接口
     */
    PageConfigGroup(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/config/group/page`, {
        params: {
          ...data
        }
      })
    },

    /*
     * 配置分组 新增接口
     */
    AddConfigGroup(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/config/group/add`, data)
    },

    /*
     * 配置分组 修改接口
     */
    EditConfigGroup(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/config/group/edit`, data)
    },

    /*
     * 配置分组 删除接口
     */
    DeleteConfigGroup(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/config/group/delete`, ids)
    },

    /*
     * 配置分组 详情接口
     */
    GetConfigGroup(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/config/group/detail/${id}`)
    },

    /*
     * 配置分组 最新接口
     */
    LatestConfigGroup(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/config/group/latest`, {
        params: {
          n
        }
      })
    },

    /*
     * 配置分组 TopN接口
     */
    TopConfigGroup(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/config/group/top`, {
        params: {
          n
        }
      })
    }
  }
}
