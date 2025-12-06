import { useAccessApi } from '@/api'
import { useRouterStore } from './RouterStore'

/**
 * 用户公开关联信息
 */
interface UserPublicAssociatedInfo {
  // USER INFO
  /** 账户ID */
  accountID: string
  /** 昵称 */
  nickname: string
  /** 头像URL */
  avatar?: string | null
  /** 性别：0-未知，1-男，2-女 */
  gender: number
  /** 生日 */
  birthday?: string | null
  /** 个性签名 */
  signature?: string | null
  /** 背景图片URL */
  background?: string | null
  /** 兴趣标签 */
  interests: any
  /** 个人网站 */
  website?: string | null
  /** GitHub 地址 */
  github?: string | null
  /** Gitee 地址 */
  gitee?: string | null
  /** 博客地址 */
  blog?: string | null

  // USER PROFILE
  /** 国家 */
  country?: string | null
  /** 省份 */
  province?: string | null
  /** 城市 */
  city?: string | null
  /** 是否显示生日 */
  showBirthday: boolean
  /** 是否显示位置 */
  showLocation: boolean

  // UserStats
  /** 用户等级 */
  level: number
  /** 当前经验值 */
  exp: number
  /** 总经验值 */
  totalExp: number
  /** 帖子数量 */
  postCount: number
  /** 评论数量 */
  commentCount: number
  /** 点赞数量 */
  likeCount: number
  /** 关注数量 */
  followCount: number
  /** 粉丝数量 */
  fansCount: number
}

export const useAuthStore = defineStore('authStore', {
  state: () => {
    return {
      token: null as string | null,
      user: null as UserPublicAssociatedInfo | null
    }
  },
  getters: {
    getToken(state): string | null {
      return state.token
    },
    getUser(state): UserPublicAssociatedInfo | null {
      return state.user
    },
    isLogined(state) {
      return !!state.token
    }
  },
  actions: {
    async setToken(token: string) {
      this.token = token
    },
    async setUser(user: UserPublicAssociatedInfo | any) {
      this.user = user
    },
    async setAuth(data: any) {
      this.setToken(data.token)
      this.setUser(data.user)
    },

    async resetAuth() {
      this.$reset()
    },

    async logoutAndRedirect() {
      const routerStore = useRouterStore()
      await useAccessApi().DoLogout()
      this.resetAuth()
      routerStore.resetRouteStore()
      MessagePlugin.info('登出成功！')
      window.location.reload()
    }
  },
  persist: {
    storage: localStorage
  }
})
