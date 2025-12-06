import { request } from '@/utils'

/**
 * 系统活动日志记录 API请求
 */
export function useSysLogApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 系统活动日志记录 分页接口
     */
    PageSysLog(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/log/page`, {
        params: {
          ...data
        }
      })
    },

    /*
     * 系统活动日志记录 新增接口
     */
    AddSysLog(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/sys/log/add`, data)
    },

    /*
     * 系统活动日志记录 修改接口
     */
    EditSysLog(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/sys/log/edit`, data)
    },

    /*
     * 系统活动日志记录 删除接口
     */
    DeleteSysLog(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/sys/log/delete`, ids)
    },

    /*
     * 系统活动日志记录 详情接口
     */
    GetSysLog(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/log/detail/${id}`)
    },

    /*
     * 系统活动日志记录 最新接口
     */
    LatestSysLog(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/log/latest`, {
        params: {
          n
        }
      })
    },

    /*
     * 系统活动日志记录 TopN接口
     */
    TopSysLog(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/log/top`, {
        params: {
          n
        }
      })
    }
  }
}
