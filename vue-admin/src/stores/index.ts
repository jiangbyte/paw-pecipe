import type { App } from 'vue'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

export * from './AuthStore.ts'
export * from './AppStore.ts'
export * from './RouterStore.ts'
export * from './TabStore.ts'

export function setupPinia(app: App) {
  const pinia = createPinia()
  pinia.use(piniaPluginPersistedstate)
  app.use(pinia)
}
