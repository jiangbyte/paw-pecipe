import { request } from '@/utils'

/**
 * 商品日常销售SKU API请求
 */
export function useBizProductSkuApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 商品日常销售SKU 分页接口
     */
    PageBizProductSku(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/product/sku/page`, {
        params: {
          ...data,
        },
      })
    },
    PageBizProductSkuWithProduct(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/product/sku/page_with_product`, {
        params: {
          ...data,
        },
      })
    },
    GetBizProductSkuWithProduct(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/product/sku/detail_with_product/${id}`)
    },

    /*
     * 商品日常销售SKU 新增接口
     */
    AddBizProductSku(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/product/sku/add`, data)
    },

    /*
     * 商品日常销售SKU 修改接口
     */
    EditBizProductSku(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/product/sku/edit`, data)
    },

    /*
     * 商品日常销售SKU 删除接口
     */
    DeleteBizProductSku(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/product/sku/delete`, ids)
    },

    /*
     * 商品日常销售SKU 详情接口
     */
    GetBizProductSku(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/product/sku/detail/${id}`)
    },

    /*
    * 商品日常销售SKU 最新接口
    */
    LatestBizProductSku(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/product/sku/latest`, {
        params: {
          n,
        },
      })
    },

    /*
    * 商品日常销售SKU TopN接口
    */
    TopBizProductSku(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/product/sku/top`, {
        params: {
          n,
        },
      })
    },
  }
}
