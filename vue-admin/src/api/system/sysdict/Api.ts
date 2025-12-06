import { request } from '@/utils'

/**
 * 系统字典 API请求
 */
export function useSysDictApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 系统字典 分页接口
     */
    PageSysDict(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/dict/page`, {
        params: {
          ...data
        }
      })
    },

    /*
     * 系统字典 新增接口
     */
    AddSysDict(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/sys/dict/add`, data)
    },

    /*
     * 系统字典 修改接口
     */
    EditSysDict(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/sys/dict/edit`, data)
    },

    /*
     * 系统字典 删除接口
     */
    DeleteSysDict(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/sys/dict/delete`, ids)
    },

    /*
     * 系统字典 详情接口
     */
    GetSysDict(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/dict/detail/${id}`)
    },

    /*
     * 系统字典 最新接口
     */
    LatestSysDict(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/dict/latest`, {
        params: {
          n
        }
      })
    },

    /*
     * 系统字典 TopN接口
     */
    TopSysDict(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/dict/top`, {
        params: {
          n
        }
      })
    },

    TreeOptions(keyword: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/dict/tree/options`, {
        params: {
          keyword
        }
      })
    },
    ListOptions(keyword: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/dict/list/options`, {
        params: {
          keyword
        }
      })
    },
    ListTypeOptions(keyword: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/dict/list/type/options`, {
        params: {
          keyword
        }
      })
    },

    ListOptionsByType(type: string, keyword?: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/dict/list/options/by/type`, {
        params: {
          type,
          ...(keyword && { keyword }) // 只有当 keyword 有值时才传入
        }
      })
    }
  }
}
