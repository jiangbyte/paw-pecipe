<script lang="ts" setup>
import CustomNavbar from '@/components/CustomNavbar.vue'
import CustomTabBar from '@/components/CustomTabBar.vue'
import { ref } from 'vue'

// 用户信息
const userInfo = ref({
  avatar: 'https://picsum.photos/seed/user/200/200',
  nickname: '美食小达人',
  signature: '爱生活，爱美食，分享美好时光',
  level: 'Lv.3',
  experience: 1280,
  nextLevelExperience: 2000,
})

// 统计数据
const stats = ref([
  { icon: '📝', label: '我的菜谱', value: 12, path: '/pages/myRecipes/index' },
  { icon: '⭐', label: '我的收藏', value: 36, path: '/pages/myCollections/index' },
  { icon: '🎁', label: '我的优惠', value: 8, path: '/pages/myCoupons/index' },
  { icon: '👑', label: '我的积分', value: 586, path: '/pages/myPoints/index' },
])

// 功能菜单
const menuList = ref([
  {
    title: '我的账户',
    icon: '👤',
    items: [
      { name: '个人资料', path: '/pages/profile/edit' },
      { name: '账号安全', path: '/pages/profile/security' },
      { name: '收货地址', path: '/pages/profile/addresses' },
      { name: '绑定手机', path: '/pages/profile/bindPhone' },
    ],
  },
  {
    title: '应用设置',
    icon: '⚙️',
    items: [
      { name: '通知设置', path: '/pages/settings/notification' },
      { name: '隐私设置', path: '/pages/settings/privacy' },
      { name: '主题切换', path: '/pages/settings/theme' },
      { name: '清除缓存', path: '/pages/settings/clearCache' },
    ],
  },
  {
    title: '关于应用',
    icon: 'ℹ️',
    items: [
      { name: '关于我们', path: '/pages/about/us' },
      { name: '帮助中心', path: '/pages/about/help' },
      { name: '意见反馈', path: '/pages/about/feedback' },
      { name: '版本信息', path: '/pages/about/version' },
    ],
  },
])

// 退出登录
function logout() {
  uni.showModal({
    title: '退出登录',
    content: '确定要退出当前账号吗？',
    confirmText: '退出',
    cancelText: '取消',
    success: (res) => {
      if (res.confirm) {
        // 实际项目中这里处理退出登录逻辑
        uni.showToast({ title: '退出成功', icon: 'success' })
        // 跳转到登录页
        // uni.redirectTo({ url: '/pages/login/index' })
      }
    },
  })
}

// 跳转页面
function navigateTo(path: string) {
  if (path) {
    uni.navigateTo({ url: path })
  }
}

// 计算经验值百分比
function getExperiencePercent() {
  return Math.min(100, Math.round((userInfo.value.experience / userInfo.value.nextLevelExperience) * 100))
}
</script>

<template>
  <view class="profile-container">
    <!-- 导航栏 -->
    <CustomNavbar title="我的" />

    <!-- 用户信息卡片 -->
    <view class="user-card">
      <view class="user-header">
        <!-- 头像 -->
        <view class="avatar-container">
          <image :src="userInfo.avatar" mode="cover" class="user-avatar" />
          <view class="user-level">
            <text class="level-text">
              {{ userInfo.level }}
            </text>
          </view>
        </view>

        <!-- 昵称和签名 -->
        <view class="user-info">
          <view class="user-nickname-container">
            <text class="user-nickname">
              {{ userInfo.nickname }}
            </text>
            <button class="edit-btn" @click="navigateTo('/pages/profile/edit')">
              <text class="edit-text">
                编辑
              </text>
            </button>
          </view>
          <text class="user-signature">
            {{ userInfo.signature }}
          </text>

          <!-- 经验条 -->
          <view class="experience-bar">
            <text class="experience-text">
              经验值：{{ userInfo.experience }}/{{ userInfo.nextLevelExperience }}
            </text>
            <view class="progress-container">
              <view class="progress-bg">
                <view
                  class="progress-fill"
                  :style="{ width: `${getExperiencePercent()}%` }"
                />
              </view>
              <text class="progress-percent">
                {{ getExperiencePercent() }}%
              </text>
            </view>
          </view>
        </view>
      </view>

      <!-- 统计数据 -->
      <view class="stats-container">
        <view
          v-for="(stat, index) in stats"
          :key="index"
          class="stat-item"
          @click="navigateTo(stat.path)"
        >
          <text class="stat-icon">
            {{ stat.icon }}
          </text>
          <text class="stat-value">
            {{ stat.value }}
          </text>
          <text class="stat-label">
            {{ stat.label }}
          </text>
        </view>
      </view>
    </view>

    <!-- 功能菜单 -->
    <view class="menu-container">
      <view
        v-for="(group, groupIndex) in menuList"
        :key="groupIndex"
        class="menu-group"
      >
        <view class="menu-group-header">
          <text class="group-icon">
            {{ group.icon }}
          </text>
          <text class="group-title">
            {{ group.title }}
          </text>
        </view>

        <view class="menu-items">
          <view
            v-for="(item, itemIndex) in group.items"
            :key="itemIndex"
            class="menu-item"
            @click="navigateTo(item.path)"
          >
            <text class="item-text">
              {{ item.name }}
            </text>
            <text class="arrow-icon">
              →
            </text>
          </view>
        </view>
      </view>
    </view>

    <!-- 退出登录按钮 -->
    <view class="logout-container">
      <button class="logout-btn" @click="logout">
        <text class="logout-text">
          退出登录
        </text>
      </button>
    </view>

    <!-- 底部标签栏 -->
    <CustomTabBar />
  </view>
</template>

<style scoped>
/* 基础容器样式 */
.profile-container {
  width: 100vw;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  padding-bottom: env(safe-area-inset-bottom);
}

/* 用户信息卡片 */
.user-card {
  background: linear-gradient(135deg, #ff7eb3, #64b5f6);
  border-radius: 0 0 32rpx 32rpx;
  padding: 30rpx 20rpx;
  color: white;
  margin-bottom: 20rpx;
  margin-right: 20rpx;
}

.user-header {
  display: flex;
  margin-bottom: 30rpx;
}

/* 头像区域 */
.avatar-container {
  position: relative;
  margin-right: 20rpx;
}

.user-avatar {
  width: 140rpx;
  height: 140rpx;
  border-radius: 50%;
  border: 4rpx solid rgba(255, 255, 255, 0.8);
  object-fit: cover;
}

.user-level {
  position: absolute;
  bottom: 0;
  right: 0;
  background-color: #ffc107;
  color: #2d3748;
  font-size: 20rpx;
  font-weight: 600;
  padding: 4rpx 12rpx;
  border-radius: 16rpx;
  border: 2rpx solid white;
}

/* 用户信息 */
.user-info {
  flex: 1;
}

.user-nickname-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8rpx;
}

.user-nickname {
  font-size: 32rpx;
  font-weight: 600;
}

.edit-btn {
  background-color: rgba(255, 255, 255, 0.3);
  color: white;
  border: none;
  border-radius: 16rpx;
  padding: 4rpx 16rpx;
  font-size: 22rpx;
  height: 44rpx;
  line-height: 44rpx;
}

.user-signature {
  font-size: 22rpx;
  opacity: 0.9;
  margin-bottom: 16rpx;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

/* 经验条 */
.experience-bar {
  background-color: rgba(255, 255, 255, 0.2);
  border-radius: 16rpx;
  padding: 12rpx;
}

.experience-text {
  font-size: 20rpx;
  margin-bottom: 8rpx;
  display: block;
}

.progress-container {
  display: flex;
  align-items: center;
  gap: 12rpx;
}

.progress-bg {
  flex: 1;
  height: 12rpx;
  background-color: rgba(255, 255, 255, 0.3);
  border-radius: 6rpx;
  overflow: hidden;
}

.progress-fill {
  height: 100%;
  background-color: #ffc107;
  border-radius: 6rpx;
  transition: width 0.5s ease;
}

.progress-percent {
  font-size: 20rpx;
  font-weight: 600;
  min-width: 60rpx;
  text-align: right;
}

/* 统计数据 */
.stats-container {
  display: flex;
  justify-content: space-around;
  background-color: rgba(255, 255, 255, 0.2);
  border-radius: 24rpx;
  padding: 20rpx 0;
}

.stat-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8rpx;
  width: 25%;
}

.stat-icon {
  font-size: 40rpx;
  margin-bottom: 4rpx;
}

.stat-value {
  font-size: 28rpx;
  font-weight: 700;
}

.stat-label {
  font-size: 22rpx;
  opacity: 0.9;
}

/* 功能菜单 */
.menu-container {
  flex: 1;
  padding: 0 20rpx;
  margin-right: 15rpx;
}

.menu-group {
  background-color: white;
  border-radius: 24rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

.menu-group-header {
  padding: 20rpx 24rpx;
  border-bottom: 1rpx solid #f0f2f5;
  display: flex;
  align-items: center;
  gap: 16rpx;
}

.group-icon {
  font-size: 32rpx;
  color: #ff7eb3;
}

.group-title {
  font-size: 26rpx;
  font-weight: 600;
  color: #2d3748;
}

.menu-items {
  padding: 8rpx 0;
}

.menu-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20rpx 24rpx;
  transition: background-color 0.3s ease;
}

.menu-item:active {
  background-color: #f5f6f7;
}

.item-text {
  font-size: 24rpx;
  color: #4a5568;
}

.arrow-icon {
  font-size: 24rpx;
  color: #a0aec0;
}

/* 退出登录按钮 */
.logout-container {
  padding: 20rpx;
  margin-bottom: 100rpx;
  margin-right: 10rpx;
}

.logout-btn {
  width: 100%;
  background-color: white;
  color: #ff5252;
  border: 2rpx solid #ff5252;
  border-radius: 32rpx;
  font-size: 26rpx;
  font-weight: 600;
  height: 80rpx;
}

.logout-btn:active {
  background-color: #fef2f2;
}

/* 动画效果 */
@keyframes fadeIn {
  from { opacity: 0; transform: translateY(20rpx); }
  to { opacity: 1; transform: translateY(0); }
}

.user-card, .menu-group, .logout-container {
  animation: fadeIn 0.5s ease forwards;
}

.user-card { animation-delay: 0.1s; }
.menu-group:nth-child(1) { animation-delay: 0.2s; }
.menu-group:nth-child(2) { animation-delay: 0.3s; }
.menu-group:nth-child(3) { animation-delay: 0.4s; }
.logout-container { animation-delay: 0.5s; }

/* 响应式适配 */
@media (min-width: 750rpx) {
  .menu-container {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 20rpx;
  }

  .stats-container {
    padding: 30rpx 0;
  }

  .stat-icon {
    font-size: 48rpx;
  }

  .stat-value {
    font-size: 32rpx;
  }

  .stat-label {
    font-size: 24rpx;
  }
}

/* 适配深色模式（可选） */
@media (prefers-color-scheme: dark) {
  .profile-container {
    background-color: #1a202c;
  }

  .menu-group, .logout-btn {
    background-color: #2d3748;
  }

  .group-title, .item-text, .logout-text {
    color: #f7fafc;
  }

  .arrow-icon {
    color: #a0aec0;
  }

  .menu-item:active {
    background-color: #384459;
  }

  .logout-btn {
    border-color: #ff7875;
    color: #ff7875;
    background-color: #2d3748;
  }

  .logout-btn:active {
    background-color: #384459;
  }
}
</style>
