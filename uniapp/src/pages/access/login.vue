<script lang="ts" setup>
import CustomNavbar from '@/components/CustomNavbar.vue'
import { ref } from 'vue'

// 表单数据
const formData = ref({
  username: '',
  password: '',
  captcha: '',
})

// 验证码图片（实际项目中替换为后端接口地址）
const captchaImg = ref('https://picsum.photos/200/80?random=1')

// 按钮状态
const isLoginBtnActive = ref(false)
const isCaptchaLoading = ref(false)

// 刷新验证码
function refreshCaptcha() {
  isCaptchaLoading.value = true
  // 模拟接口请求延迟
  setTimeout(() => {
    // 随机数刷新验证码图片（实际项目中调用后端刷新接口）
    captchaImg.value = `https://picsum.photos/200/80?random=${Math.random()}`
    formData.value.captcha = ''
    isCaptchaLoading.value = false
  }, 500)
}

// 登录处理
function handleLogin() {
  // 表单验证
  if (!formData.value.username) {
    uni.showToast({ title: '请输入用户名', icon: 'none' })
    return
  }
  if (!formData.value.password) {
    uni.showToast({ title: '请输入密码', icon: 'none' })
    return
  }
  if (!formData.value.captcha) {
    uni.showToast({ title: '请输入验证码', icon: 'none' })
    return
  }

  uni.showLoading({ title: '登录中...' })

  // 模拟登录请求
  setTimeout(() => {
    uni.hideLoading()
    uni.showToast({ title: '登录成功', icon: 'success' })
    // 登录成功后的逻辑，如跳转首页
    // uni.switchTab({ url: '/pages/index/index' })
  }, 1500)
}

// 监听输入框回车事件
function handleEnterKey() {
  handleLogin()
}
</script>

<template>
  <view class="container">
    <CustomNavbar title="用户登录" />

    <!-- 装饰元素 -->
    <view class="decor decor-left" />
    <view class="decor decor-right" />

    <!-- 登录表单 -->
    <view class="content">
      <view class="login-card">
        <view class="login-title">
          <text class="title-text">
            欢迎登录
          </text>
          <text class="title-desc">
            请输入您的账号信息
          </text>
        </view>

        <!-- 用户名输入框 -->
        <view class="input-item">
          <text class="input-label">
            用户名
          </text>
          <input
            v-model="formData.username"
            class="input-content"
            type="text"
            placeholder="请输入用户名/手机号"
            placeholder-class="input-placeholder"
            @confirm="handleEnterKey"
          >
        </view>

        <!-- 密码输入框 -->
        <view class="input-item">
          <text class="input-label">
            密码
          </text>
          <input
            v-model="formData.password"
            class="input-content"
            type="password"
            placeholder="请输入密码"
            placeholder-class="input-placeholder"
            @confirm="handleEnterKey"
          >
        </view>

        <!-- 验证码输入框 -->
        <view class="input-item captcha-item">
          <view class="captcha-left">
            <text class="input-label">
              验证码
            </text>
            <input
              v-model="formData.captcha"
              class="input-content captcha-input"
              type="text"
              maxlength="4"
              placeholder="请输入验证码"
              placeholder-class="input-placeholder"
              @confirm="handleEnterKey"
            >
          </view>
          <view class="captcha-img-wrap" @click="refreshCaptcha">
            <image
              v-if="!isCaptchaLoading"
              class="captcha-img"
              :src="captchaImg"
              mode="aspectFill"
            />
            <view v-else class="captcha-loading">
              <text class="loading-text">
                刷新中...
              </text>
            </view>
            <text class="refresh-text">
              点击刷新
            </text>
          </view>
        </view>

        <!-- 登录按钮 -->
        <view
          class="login-btn"
          @click="handleLogin"
          @touchstart="isLoginBtnActive = true"
          @touchend="isLoginBtnActive = false"
        >
          <text class="btn-text">
            登录
          </text>
        </view>

        <!-- 其他操作 -->
        <view class="login-actions">
          <text class="action-text">
            忘记密码？
          </text>
          <text class="action-text">
            注册账号
          </text>
        </view>
      </view>
    </view>
  </view>
</template>

<style scoped>
/* 基础容器样式 - 继承原有风格 */
.container {
  width: 100vw;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

/* 导航栏样式 */
.navbar {
  position: relative;
  z-index: 10;
}

/* 装饰元素 - 复用原有动画效果 */
.decor {
  position: absolute;
  width: 200rpx;
  height: 200rpx;
  border-radius: 50%;
  opacity: 0.15;
  z-index: 0;
}

.decor-left {
  top: 15%;
  left: -80rpx;
  background: linear-gradient(135deg, #ff6b8b, #ff8e53);
  animation: float 8s ease-in-out infinite;
}

.decor-right {
  bottom: 20%;
  right: -80rpx;
  background: linear-gradient(135deg, #4facfe, #00f2fe);
  animation: float 10s ease-in-out infinite reverse;
}

/* 主内容区 */
.content {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40rpx 30rpx;
  position: relative;
  z-index: 0;
}

/* 登录卡片 */
.login-card {
  width: 100%;
  max-width: 600rpx;
  border-radius: 24rpx;
  padding: 60rpx 40rpx;
  position: relative;
}

/* 登录标题 */
.login-title {
  text-align: center;
  margin-bottom: 60rpx;
}

.title-text {
  font-size: 36rpx;
  font-weight: 600;
  color: #2d3748;
  display: block;
  margin-bottom: 12rpx;
}

.title-desc {
  font-size: 24rpx;
  color: #718096;
  line-height: 1.5;
}

/* 输入项样式 */
.input-item {
  margin-bottom: 40rpx;
  position: relative;
}

.input-label {
  font-size: 26rpx;
  color: #4a5568;
  font-weight: 500;
  display: block;
  margin-bottom: 12rpx;
}

.input-content {
  width: 100%;
  height: 80rpx;
  border: 2rpx solid #e2e8f0;
  border-radius: 12rpx;
  padding: 0 20rpx;
  font-size: 28rpx;
  color: #2d3748;
  background-color: #fafafa;
  transition: all 0.3s ease;
}

.input-content:focus {
  border-color: #64b5f6;
  background-color: #ffffff;
  box-shadow: 0 0 0 4rpx rgba(100, 181, 246, 0.1);
}

.input-placeholder {
  color: #a0aec0;
  font-size: 26rpx;
}

/* 验证码项样式 */
.captcha-item {
  display: flex;
  align-items: center;
  gap: 20rpx;
}

.captcha-left {
  flex: 1;
}

.captcha-input {
  height: 80rpx;
}

.captcha-img-wrap {
  width: 200rpx;
  height: 80rpx;
  margin-top: 52rpx;
  position: relative;
  border-radius: 12rpx;
  overflow: hidden;
  border: 2rpx solid #e2e8f0;
}

.captcha-img {
  width: 100%;
  height: 100%;
}

.captcha-loading {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #fafafa;
}

.loading-text {
  font-size: 22rpx;
  color: #718096;
}

.refresh-text {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 30rpx;
  line-height: 30rpx;
  text-align: center;
  font-size: 20rpx;
  color: rgba(255, 255, 255, 0.9);
  background-color: rgba(0, 0, 0, 0.4);
}

/* 登录按钮 */
.login-btn {
  width: 100%;
  height: 90rpx;
  background: linear-gradient(135deg, #ff7eb3, #64b5f6);
  border-radius: 12rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 20rpx;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  box-shadow: 0 8rpx 16rpx rgba(100, 181, 246, 0.2);
}

.login-btn:active {
  transform: scale(0.98);
  box-shadow: 0 4rpx 8rpx rgba(100, 181, 246, 0.2);
}

.btn-text {
  font-size: 32rpx;
  font-weight: 500;
  color: #ffffff;
  letter-spacing: 2rpx;
}

/* 登录操作项 */
.login-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 30rpx;
}

.action-text {
  font-size: 24rpx;
  color: #4299e1;
}

/* 动画定义 - 复用原有动画 */
@keyframes float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-20rpx);
  }
}
</style>
