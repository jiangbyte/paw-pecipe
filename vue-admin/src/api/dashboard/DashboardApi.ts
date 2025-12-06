import { request } from '@/utils'

export function useDashboardApi() {
  const pathPrefix = `/api/v1`
  return {
    GetDashboardPaneItems() {
      return request.Get<IResult<any>>(`${pathPrefix}/dashboard/pane/items`)
    }
  }
}
