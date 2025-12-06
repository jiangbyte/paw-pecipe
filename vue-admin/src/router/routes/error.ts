import type { RouteRecordRaw } from 'vue-router'

export const errorRoutes: RouteRecordRaw[] = [
  {
    path: '/:pathMatch(.*)*',
    name: '404',
    component: () => import('@/views/code/404.vue'),
    meta: { title: 'Not Found' }
  }
]
