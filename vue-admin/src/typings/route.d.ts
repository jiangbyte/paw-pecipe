declare namespace SiteRoute {
  interface RouteMeta {
    title?: string
    icon?: string
    sort?: number
    keepAlive?: boolean
    visible?: boolean
    pined?: boolean
    menuType?: number
    parameters?: string
    extraParams?: object
    withoutTab?: boolean

    externalUrl?: string
    openTarget?: number
    iframeAttrs?: string
    redirect?: string

    isDeleted?: boolean
    deletedAt?: string
    deletedBy?: string
    createdAt?: string
    createdBy?: string
    updatedAt?: string
    updatedBy?: string

    componentPath?: string
  }

  type MetaKeys = keyof RouteMeta

  interface BaseRoute {
    id: string
    pid: string
    name: string
    path: string
    redirect?: string
  }

  type RowRoute = RouteMeta & BaseRoute

  interface Route extends BaseRoute {
    children?: Route[]
    component: any
    meta: RouteMeta
  }
}
