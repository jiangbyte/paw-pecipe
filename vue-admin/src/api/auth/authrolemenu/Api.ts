import { request } from '@/utils'

/**
 * 角色菜单关联 API请求
 */
export function useAuthRoleMenuApi() {
  const pathPrefix = '/api/v1'

  return {
    /*
     * 角色菜单关联 分页接口
     */
    PageAuthRoleMenu(data: any) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/role/menu/page`, {
        params: {
          ...data
        }
      })
    },

    /*
     * 角色菜单关联 新增接口
     */
    AddAuthRoleMenu(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/auths/role/menu/add`, data)
    },

    /*
     * 角色菜单关联 修改接口
     */
    EditAuthRoleMenu(data: any) {
      return request.Post<IResult<any>>(`${pathPrefix}/auths/role/menu/edit`, data)
    },

    /*
     * 角色菜单关联 删除接口
     */
    DeleteAuthRoleMenu(ids: string[]) {
      return request.Post<IResult<any>>(`${pathPrefix}/auths/role/menu/delete`, ids)
    },

    /*
     * 角色菜单关联 详情接口
     */
    GetAuthRoleMenu(id: string) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/role/menu/detail/${id}`)
    },

    /*
     * 角色菜单关联 最新接口
     */
    LatestAuthRoleMenu(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/role/menu/latest`, {
        params: {
          n
        }
      })
    },

    /*
     * 角色菜单关联 TopN接口
     */
    TopAuthRoleMenu(n: number) {
      return request.Get<IResult<any>>(`${pathPrefix}/auths/role/menu/top`, {
        params: {
          n
        }
      })
    }
  }
}
