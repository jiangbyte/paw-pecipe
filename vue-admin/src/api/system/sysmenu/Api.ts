import { request } from '@/utils'

/**
 * 菜单 API请求
 */
export function useSysMenuApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 菜单 分页接口
     */
    PageSysMenu(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/menu/page`, {
        params: {
          ...data
        }
      })
    },

    /*
     * 菜单 新增接口
     */
    AddSysMenu(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/sys/menu/add`, data)
    },

    /*
     * 菜单 修改接口
     */
    EditSysMenu(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/sys/menu/edit`, data)
    },

    /*
     * 菜单 删除接口
     */
    DeleteSysMenu(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/sys/menu/delete`, ids)
    },

    /*
     * 菜单 详情接口
     */
    GetSysMenu(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/menu/detail/${id}`)
    },

    /*
     * 菜单 最新接口
     */
    LatestSysMenu(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/menu/latest`, {
        params: {
          n
        }
      })
    },

    /*
     * 菜单 TopN接口
     */
    TopSysMenu(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/menu/top`, {
        params: {
          n
        }
      })
    },

    GetSysMenuListTreeWithAccountID() {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/menu/list/tree`)
    },
    GetSysMenuListWithAccountID() {
      return request.Get<IResult<any>>(`${pathPrefix}/sys/menu/list`)
    }
  }
}
