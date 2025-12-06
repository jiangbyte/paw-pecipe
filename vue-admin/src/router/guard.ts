import { useAuthStore, useRouterStore, useTabStore } from '@/stores'
import type { Router } from 'vue-router'

import 'nprogress/nprogress.css'
import NProgress from 'nprogress'

export function setupRouterGuard(router: Router) {
  NProgress.configure({ showSpinner: false })
  const authStore = useAuthStore()
  const routerStore = useRouterStore()
  const tabStore = useTabStore()

  const publicRoutes = ['/', '/404'] // 所有人都可以访问的页面
  const authPages = ['login', 'forget', 'pwdreset', 'register'] // 认证相关页面（登录/注册/忘记密码等）

  router.beforeEach(async (to, _from, next) => {
    // 外链菜单处理
    if (to.meta?.externalUrl) {
      console.log('检测到外链菜单，进行跳转', to.meta.externalUrl)
      const externalUrl = to.meta.externalUrl as string
      const openTarget = to.meta.openTarget || 0 // 默认当前窗口打开

      if (openTarget === 1) {
        // 新窗口打开
        window.open(externalUrl, '_blank')
        next(false)
      } else {
        // 当前窗口打开
        window.location.href = externalUrl
        next(false)
      }
      NProgress.done()
      return
    }

    // iframe嵌入菜单处理
    if (to.meta?.menuType === 3) {
      console.log('检测到iframe菜单，直接放行')
      next()
      return
    }

    // 重定向菜单处理
    if (to.meta?.menuType === 2 && to.meta?.redirect) {
      console.log('检测到重定向菜单，跳转到:', to.meta.redirect)
      next(to.meta.redirect as string)
      return
    }

    // 加载条
    NProgress.start()

    const isPublicRoute = publicRoutes.includes(to.path)
    const isAuthPage = authPages.includes(String(to.name))
    const isLogined = authStore.isLogined

    // 情况1：公开路由 → 直接放行
    if (isPublicRoute) {
      console.log('公开路径直接放行')
      next()
      return
    }

    // 情况2：未登录用户
    if (!isLogined) {
      console.log('未登录用户')
      // 未登录且访问的是认证页面 → 允许访问（比如登录页）
      if (isAuthPage) {
        console.log('未登录用户访问认证页面 → 允许')
        next()
        return
      }
      // 未登录且访问非公开页面 → 跳转到登录页
      console.log('未登录用户访问需要权限的页面 → 跳转到登录页')
      const redirect = to.name === '404' ? undefined : to.fullPath
      next({ path: '/login', query: { redirect } })
      return
    }

    // 情况3：已登录用户访问认证页面 → 重定向到首页
    if (isLogined && isAuthPage) {
      console.log('已登录用户访问认证页面 → 重定向到首页')
      next({ path: '/' })
      return
    }

    // 路由初始化
    if (!routerStore.isInit) {
      try {
        await routerStore.initRouter()

        // 初始化后检查路由数据
        if (routerStore.rowRoutes.length === 0 || routerStore.menus.length === 0) {
          console.warn('路由初始化后无有效路由，退出登录')
          authStore.$reset()
          next({ path: '/login' })
          return
        }
        // 如果是404页面，重新跳转以匹配新路由
        if (to.name === '404') {
          console.log('路由初始化后处理404页面')
          next({
            path: to.fullPath,
            replace: true,
            query: to.query,
            hash: to.hash
          })
          return
        }

        // 重新匹配当前路由（因为路由表已更新）
        const currentPath = to.fullPath
        const exists = routerStore.rowRoutes.some(
          route => route.path === to.path || route.path === currentPath
        )

        if (!exists) {
          console.log('路由不存在，跳转到404')
          next('/404')
          return
        }

        console.log('路由初始化完成，继续导航')
        next()
        return
      } catch (error) {
        console.error('路由初始化失败:', error)
        authStore.$reset()
        next({ path: '/login' })
        return
      }
    }

    // 其他情况：已登录、已初始化、访问正常页面 → 放行
    console.log('已登录用户访问正常页面 → 放行')
    next()
  })

  router.beforeResolve(to => {
    routerStore.setActiveMenu(to.path)
    tabStore.addTab(to)
    tabStore.setCurrent(to.path)
  })

  router.afterEach(to => {
    document.title = `${to.meta.title || '默认标题'}`
    NProgress.done()
  })
}
