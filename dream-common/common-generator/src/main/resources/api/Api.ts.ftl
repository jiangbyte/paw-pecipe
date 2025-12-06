import { request } from '@/utils'

/**
* ${table.comment?replace('表', '')} API请求
*/
export function use${entity}Api() {
    const pathPrefix = '/api/v1'

 return {
    /*
     * ${table.comment?replace('表', '')} 分页接口
     */
    Page${entity}(data: any) {
      return request.Get<IResult<any>>(`<#noparse>${pathPrefix}</#noparse>/${table.name?replace('_', '/')}/page`, {
        params: {
        ...data,
        },
      })
    },

    /*
     * ${table.comment?replace('表', '')} 新增接口
     */
    Add${entity}(data: any) {
      return request.Post<IResult<any>>(`<#noparse>${pathPrefix}</#noparse>/${table.name?replace('_', '/')}/add`, data)
    },

    /*
     * ${table.comment?replace('表', '')} 修改接口
     */
    Edit${entity}(data: any) {
      return request.Post<IResult<any>>(`<#noparse>${pathPrefix}</#noparse>/${table.name?replace('_', '/')}/edit`, data)
    },

    /*
     * ${table.comment?replace('表', '')} 删除接口
     */
    Delete${entity}(ids: string[]) {
      return request.Post<IResult<any>>(`<#noparse>${pathPrefix}</#noparse>/${table.name?replace('_', '/')}/delete`, ids)
    },

    /*
     * ${table.comment?replace('表', '')} 详情接口
     */
    Get${entity}(id: string) {
      return request.Get<IResult<any>>(`<#noparse>${pathPrefix}</#noparse>/${table.name?replace('_', '/')}/detail/<#noparse>${id}</#noparse>`)
    },

    /*
    * ${table.comment?replace('表', '')} 最新接口
    */
    Latest${entity}(n: number) {
      return request.Get<IResult<any>>(`<#noparse>${pathPrefix}</#noparse>/${table.name?replace('_', '/')}/latest`, {
        params: {
            n,
        },
      })
    },

    /*
    * ${table.comment?replace('表', '')} TopN接口
    */
    Top${entity}(n: number) {
      return request.Get<IResult<any>>(`<#noparse>${pathPrefix}</#noparse>/${table.name?replace('_', '/')}/top`, {
        params: {
            n,
        },
      })
    },
  }
}
