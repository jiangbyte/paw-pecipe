import { useConfigItemApi } from '@/api'
import themeJson from './theme.json'

// 社交链接类型
interface SocialLink {
  image: string
  title: string
  url: string
}

// 网站配置信息类型
interface WebsiteConfigInfo {
  // 网站信息
  websiteName: string
  websiteLogo: string
  websiteDescription: string
  websiteKeywords: string
  websiteAuthor: string
  websiteCopyright: string
  websiteVersion: string

  // 联系信息
  contactQQ: string
  contactEmail: string
  contactWeChat: string

  // 社交链接
  socialLinks: SocialLink[]
}

export const useAppStore = defineStore('appStore', {
  state: () => {
    return {
      themeOverrides: themeJson,
      website: null as WebsiteConfigInfo | null,
      lastFetchTime: 0,
      isFetching: false,
      hasAutoFetched: false, // 标记是否已自动获取过
      collapsed: true
    }
  },
  getters: {
    shouldRefreshConfig: state => {
      const CACHE_DURATION = 5 * 60 * 1000
      return !state.website || Date.now() - state.lastFetchTime > CACHE_DURATION
    },

    // 计算属性，访问时自动触发获取
    websiteConfig: state => {
      const store = useAppStore()

      // 如果没有获取过且需要刷新，自动触发获取
      if (!store.hasAutoFetched && store.shouldRefreshConfig && !state.isFetching) {
        store.hasAutoFetched = true
        store.fetchWebsiteConfig()
      }

      return state.website
    }
  },
  actions: {
    toggleCollapse() {
      this.collapsed = !this.collapsed
    },
    async fetchWebsiteConfig(forceRefresh = false) {
      if (this.isFetching && !forceRefresh) {
        return this.website
      }

      if (!forceRefresh && !this.shouldRefreshConfig) {
        return this.website
      }

      try {
        this.isFetching = true
        const { data } = await useConfigItemApi().GetWebsiteConfig()
        this.website = data
        this.lastFetchTime = Date.now()
        return data
      } catch (error) {
        console.error('获取网站配置失败:', error)
        throw error
      } finally {
        this.isFetching = false
      }
    },

    async refreshWebsiteConfig() {
      return this.fetchWebsiteConfig(true)
    }
  },
  persist: {
    storage: localStorage
  }
})
