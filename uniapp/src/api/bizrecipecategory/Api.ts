import { request } from '@/utils'

/**
 * 菜谱分类 API请求
 */
export function useBizRecipeCategoryApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 菜谱分类 分页接口
     */
    PageBizRecipeCategory(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/recipe/category/page`, {
        params: {
          ...data,
        },
      })
    },
    GetLists() {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/recipe/category/lists`)
    },
    /*
     * 菜谱分类 新增接口
     */
    AddBizRecipeCategory(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/recipe/category/add`, data)
    },

    /*
     * 菜谱分类 修改接口
     */
    EditBizRecipeCategory(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/recipe/category/edit`, data)
    },

    /*
     * 菜谱分类 删除接口
     */
    DeleteBizRecipeCategory(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/recipe/category/delete`, ids)
    },

    /*
     * 菜谱分类 详情接口
     */
    GetBizRecipeCategory(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/recipe/category/detail/${id}`)
    },

    /*
    * 菜谱分类 最新接口
    */
    LatestBizRecipeCategory(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/recipe/category/latest`, {
        params: {
          n,
        },
      })
    },

    /*
    * 菜谱分类 TopN接口
    */
    TopBizRecipeCategory(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/recipe/category/top`, {
        params: {
          n,
        },
      })
    },
  }
}
