import { request } from '@/utils'

/**
 * 商品分类 API请求
 */
export function useBizProductCategoryApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 商品分类 分页接口
     */
    PageBizProductCategory(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/product/category/page`, {
        params: {
          ...data,
        },
      })
    },
    GetLists() {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/product/category/lists`)
    },

    /*
     * 商品分类 新增接口
     */
    AddBizProductCategory(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/product/category/add`, data)
    },

    /*
     * 商品分类 修改接口
     */
    EditBizProductCategory(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/product/category/edit`, data)
    },

    /*
     * 商品分类 删除接口
     */
    DeleteBizProductCategory(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/product/category/delete`, ids)
    },

    /*
     * 商品分类 详情接口
     */
    GetBizProductCategory(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/product/category/detail/${id}`)
    },

    /*
    * 商品分类 最新接口
    */
    LatestBizProductCategory(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/product/category/latest`, {
        params: {
          n,
        },
      })
    },

    /*
    * 商品分类 TopN接口
    */
    TopBizProductCategory(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/product/category/top`, {
        params: {
          n,
        },
      })
    },
  }
}
