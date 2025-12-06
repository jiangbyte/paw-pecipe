import type { RouteRecordRaw } from 'vue-router'

export const layoutRoutes: RouteRecordRaw[] = [
  {
    path: '/',
    name: 'default',
    redirect: '/root',
    component: () => import('@/layouts/index.vue'),
    children: []
  }
]
