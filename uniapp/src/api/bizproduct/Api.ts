import { request } from '@/utils'

/**
 * 商品主 API请求
 */
export function useBizProductApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 商品主 分页接口
     */
    PageBizProduct(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/product/page`, {
        params: {
          ...data,
        },
      })
    },

    /*
     * 商品主 新增接口
     */
    AddBizProduct(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/product/add`, data)
    },

    /*
     * 商品主 修改接口
     */
    EditBizProduct(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/product/edit`, data)
    },

    /*
     * 商品主 删除接口
     */
    DeleteBizProduct(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/product/delete`, ids)
    },

    /*
     * 商品主 详情接口
     */
    GetBizProduct(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/product/detail/${id}`)
    },

    /*
    * 商品主 最新接口
    */
    LatestBizProduct(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/product/latest`, {
        params: {
          n,
        },
      })
    },

    /*
    * 商品主 TopN接口
    */
    TopBizProduct(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/product/top`, {
        params: {
          n,
        },
      })
    },
  }
}
