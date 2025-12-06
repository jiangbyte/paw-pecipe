import type { RouteRecordRaw } from 'vue-router'

import { layoutRoutes } from './routes/layout'
import { authRoutes } from './routes/auth'
import { errorRoutes } from './routes/error'

export const routes: RouteRecordRaw[] = [...layoutRoutes, ...authRoutes, ...errorRoutes]
