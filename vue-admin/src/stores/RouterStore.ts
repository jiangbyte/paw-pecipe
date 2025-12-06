import { router } from '@/router'
import type { RouteRecordRaw } from 'vue-router'
import { clone, min, omit, pick } from 'radash'
import Layout from '@/layouts/index.vue'
import { useSysMenuApi } from '@/api'
import { arrayTree } from '@/utils'
import { STATIC_ROUTES } from './StaticRouter'

interface RoutesState {
  isInit: boolean
  menus: SiteRoute.Route[]
  rowRoutes: SiteRoute.RowRoute[]
  activeMenu: string | null | any
  cacheRoutes: string[]
}

export const useRouterStore = defineStore('route-store', {
  state: (): RoutesState => ({
    isInit: false,
    activeMenu: '',
    menus: [],
    rowRoutes: [],
    cacheRoutes: []
  }),

  actions: {
    async initAuthRoute() {
      if (import.meta.env.VITE_ROUTES_MODE === 'static') {
        return STATIC_ROUTES
      }
      const { data } = await useSysMenuApi().GetSysMenuListWithAccountID()
      console.log('认证菜单-->', data)
      return data || []
    },

    async initRouter() {
      this.isInit = false
      const rowRoutes = await this.initAuthRoute()
      if (!rowRoutes || rowRoutes.length === 0) {
        console.error('获取菜单数据失败')
        return
      }

      this.rowRoutes = rowRoutes

      const routes = createRoutes(rowRoutes)
      router.addRoute(routes)

      // 验证路由是否添加成功
      this.menus = createMenus(rowRoutes)

      console.log(this.menus)

      this.isInit = true
    },

    resetRouteStore() {
      this.resetRoutes()
      this.$reset()
    },

    resetRoutes() {
      if (router.hasRoute('root')) {
        router.removeRoute('root')
      }
    },

    setActiveMenu(key: string) {
      this.activeMenu = key
    }
  }
})

const metaFields: SiteRoute.MetaKeys[] = [
  'title',
  'icon',
  'sort',
  'keepAlive',
  'visible',
  'pined',
  'menuType',
  'parameters',
  'extraParams',
  'withoutTab',

  'externalUrl',
  'openTarget',
  'iframeAttrs',
  'redirect',

  'deletedAt',
  'deletedBy',
  'createdAt',
  'createdBy',
  'updatedAt',
  'updatedBy',
  'isDeleted',
  
  'componentPath'
]

function standardizedRoutes(routes: SiteRoute.RowRoute[]) {
  return clone(routes).map((i: any) => ({
    ...omit(i, metaFields),
    meta: pick(i, metaFields)
  })) as SiteRoute.Route[]
}

function createRoutes(routes: SiteRoute.RowRoute[]): RouteRecordRaw {
  const modules = import.meta.glob('@/views/**/*.vue')
  const resultRouter = arrayTree(
    standardizedRoutes(routes).map(item => ({
      ...item,
      component:
        item.meta.componentPath && !item.meta.redirect
          ? modules[`/src/views${item.meta.componentPath}`]
          : undefined
    }))
  ) as SiteRoute.Route[]

  console.log('标准化后的路由表:', resultRouter)

  setRedirect(resultRouter)

  return {
    path: '/root',
    name: 'root',
    redirect: import.meta.env.VITE_HOME_PATH,
    component: Layout,
    meta: {
      title: '系统',
      icon: 'application'
    },
    children: resultRouter as unknown as RouteRecordRaw[]
  }
}

function setRedirect(routes: SiteRoute.Route[]) {
  routes.forEach(route => {
    if (route.children?.length) {
      console.log(route.meta.title, '有子路由:', route.children)
      if (!route.redirect) {
        console.log(route.meta.title, '无跳转')
        const visibleChilds = route.children.filter(child => child.meta.visible)
        // 当有至少一个可见子菜单时就应该设置重定向
        if (visibleChilds.length >= 1) {
          let target: SiteRoute.Route | null = visibleChilds[0] as unknown as SiteRoute.Route

          // 如果有多个可见子菜单，按排序选择第一个
          if (visibleChilds.length > 1) {
            const orderChilds = visibleChilds.filter(child =>
              Number(child.meta.sort)
            ) as any as SiteRoute.Route[]
            const sortedTarget = orderChilds.length
              ? min(orderChilds, (i: SiteRoute.Route) => Number(i.meta.sort)!)
              : visibleChilds[0]

            // 确保 target 不是 null
            if (sortedTarget) {
              target = sortedTarget
            }
          }

          if (target) {
            route.redirect = target.path
            console.log(route.meta.title, '设置重定向到:', target.path)
          }
        } else {
          console.log(route.meta.title, '没有可见的子菜单，无法设置重定向')
        }
      }
      console.log(route.meta.title, '最终跳转:', route.redirect)
      setRedirect(route.children)
    }
  })
}

function createMenus(userRoutes: SiteRoute.RowRoute[]) {
  const fullTree = arrayTree(
    standardizedRoutes(userRoutes)
      // 排序
      .sort((a, b) => (Number(a.meta.sort) || 0) - (Number(b.meta.sort) || 0))
  )
  // 递归过滤：如果父级不可见，子级也要隐藏
  const filterVisibleMenus = (menus: any[]): any[] => {
    return (
      menus
        .filter(menu => {
          // 如果当前菜单不可见，直接过滤掉
          if (!menu.meta.visible) {
            return false
          }

          // 如果有子菜单，递归处理
          if (menu.children) {
            menu.children = filterVisibleMenus(menu.children)
          }

          return true
        })
        // 排序
        .sort((a, b) => (Number(a.meta.sort) || 0) - (Number(b.meta.sort) || 0))
    )
  }

  return filterVisibleMenus(fullTree)
}
