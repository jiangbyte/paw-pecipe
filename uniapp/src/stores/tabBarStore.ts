// stores/tabBarStore.ts
import { defineStore } from 'pinia'

export const useTabBarStore = defineStore('tabBar', {
  state: () => ({
    currentTab: 'pages/camera/index' as string,
  }),
  actions: {
    setCurrentTab(tabValue: string) {
      this.currentTab = tabValue
    },
  },
  // persist: {
  //   storage: localStorage
  // }

})
