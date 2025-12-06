import { request } from '@/utils'

/**
 * 限时抢购活动 API请求
 */
export function useBizFlashSaleApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 限时抢购活动 分页接口
     */
    PageBizFlashSale(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/flash/sale/page`, {
        params: {
          ...data,
        },
      })
    },

    /*
     * 限时抢购活动 新增接口
     */
    AddBizFlashSale(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/flash/sale/add`, data)
    },

    /*
     * 限时抢购活动 修改接口
     */
    EditBizFlashSale(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/flash/sale/edit`, data)
    },

    /*
     * 限时抢购活动 删除接口
     */
    DeleteBizFlashSale(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/flash/sale/delete`, ids)
    },

    /*
     * 限时抢购活动 详情接口
     */
    GetBizFlashSale(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/flash/sale/detail/${id}`)
    },

    /*
    * 限时抢购活动 最新接口
    */
    LatestBizFlashSale(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/flash/sale/latest`, {
        params: {
          n,
        },
      })
    },

    /*
    * 限时抢购活动 TopN接口
    */
    TopBizFlashSale(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/flash/sale/top`, {
        params: {
          n,
        },
      })
    },
  }
}
