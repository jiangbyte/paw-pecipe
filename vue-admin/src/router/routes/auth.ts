import type { RouteRecordRaw } from 'vue-router'

export const authRoutes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/authentication/login.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/forget',
    name: 'forget',
    component: () => import('@/views/authentication/forget.vue'),
    meta: { title: '忘记密码' }
  },
  {
    path: '/pwdreset',
    name: 'pwdreset',
    component: () => import('@/views/authentication/reset.vue'),
    meta: { title: '重置密码' }
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/authentication/register.vue'),
    meta: { title: '注册' }
  }
]
