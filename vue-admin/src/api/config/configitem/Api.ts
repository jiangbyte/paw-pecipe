import { request } from '@/utils'

/**
 * 系统配置 API请求
 */
export function useConfigItemApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 系统配置 分页接口
     */
    PageConfigItem(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/config/item/page`, {
        params: {
          ...data
        }
      })
    },

    /*
     * 系统配置 新增接口
     */
    AddConfigItem(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/config/item/add`, data)
    },

    /*
     * 系统配置 修改接口
     */
    EditConfigItem(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/config/item/edit`, data)
    },

    /*
     * 系统配置 删除接口
     */
    DeleteConfigItem(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/config/item/delete`, ids)
    },

    /*
     * 系统配置 详情接口
     */
    GetConfigItem(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/config/item/detail/${id}`)
    },

    /*
     * 系统配置 最新接口
     */
    LatestConfigItem(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/config/item/latest`, {
        params: {
          n
        }
      })
    },

    /*
     * 系统配置 TopN接口
     */
    TopConfigItem(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/config/item/top`, {
        params: {
          n
        }
      })
    },
    GetWebsiteConfig() {
      return request.Get<IResult<any>>(`${pathPrefix}/config/item/website`)
    }
  }
}
