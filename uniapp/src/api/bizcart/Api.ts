import { request } from '@/utils'

/**
 * 购物车 API请求
 */
export function useBizCartApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 购物车 分页接口
     */
    PageBizCart(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/cart/page`, {
        params: {
          ...data,
        },
      })
    },
    AddToCart(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/cart/add_to_cart`, data)
    },

    /*
     * 购物车 新增接口
     */
    AddBizCart(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/cart/add`, data)
    },

    /*
     * 购物车 修改接口
     */
    EditBizCart(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/cart/edit`, data)
    },

    /*
     * 购物车 删除接口
     */
    DeleteBizCart(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/cart/delete`, ids)
    },

    /*
     * 购物车 详情接口
     */
    GetBizCart(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/cart/detail/${id}`)
    },

    /*
    * 购物车 最新接口
    */
    LatestBizCart(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/cart/latest`, {
        params: {
          n,
        },
      })
    },

    /*
    * 购物车 TopN接口
    */
    TopBizCart(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/cart/top`, {
        params: {
          n,
        },
      })
    },
  }
}
