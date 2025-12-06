import { request } from '@/utils'

/**
 * 菜谱 API请求
 */
export function useBizRecipeApi() {
  const pathPrefix = '/api/v1'

  return {
    GenerateRecipes(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/recipes/generate`, data)
    },
    /*
     * 菜谱 分页接口
     */
    PageBizRecipe(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/recipe/page`, {
        params: {
          ...data,
        },
      })
    },

    /*
     * 菜谱 新增接口
     */
    AddBizRecipe(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/recipe/add`, data)
    },

    /*
     * 菜谱 修改接口
     */
    EditBizRecipe(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/recipe/edit`, data)
    },

    /*
     * 菜谱 删除接口
     */
    DeleteBizRecipe(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/biz/recipe/delete`, ids)
    },

    /*
     * 菜谱 详情接口
     */
    GetBizRecipe(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/recipe/detail/${id}`)
    },

    /*
    * 菜谱 最新接口
    */
    LatestBizRecipe(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/recipe/latest`, {
        params: {
          n,
        },
      })
    },

    /*
    * 菜谱 TopN接口
    */
    TopBizRecipe(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/biz/recipe/top`, {
        params: {
          n,
        },
      })
    },
  }
}
