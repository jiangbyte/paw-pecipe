<script lang="ts" setup>
import CustomNavbar from '@/components/CustomNavbar.vue'
import CustomTabBar from '@/components/CustomTabBar.vue'
import { ref } from 'vue'

function handleTakePhoto() {
  uni.chooseImage({
    count: 1,
    sourceType: ['camera'],
    sizeType: ['original'], // 保留原图，保证识别精度
    success: (res) => {
      const tempFilePath = res.tempFilePaths[0] // 临时图片路径（关键：后续传递给结果页）

      console.log(res)

      uni.showLoading({ title: '识别中...' })

      const oonx_url = import.meta.env.VITE_ONNX_API_URL
      // 使用 uploadFile 上传
      uni.uploadFile({
        url: oonx_url,
        filePath: tempFilePath,
        name: 'file', // 后端接收的字段名，必须与 API 要求一致
        formData: {
          top_k: '3',
        },
        success: (uploadRes) => {
          uni.hideLoading()
          if (uploadRes.statusCode === 200) {
            try {
              const predictData = JSON.parse(uploadRes.data)
              console.log('识别结果:', predictData)

              // 关键：存储 识别结果 + 临时图片路径（供结果页使用）
              uni.setStorageSync('latestPredictionData', {
                imagePath: tempFilePath, // 临时图片路径
                predictResult: predictData, // 识别结果
              })

              // 跳转时不带参数
              uni.navigateTo({
                url: '/pages/cookbook/config',
              })
            }
            catch (e) {
              console.error('解析响应失败', e)
              uni.showToast({ title: '识别失败', icon: 'none' })
            }
          }
          else {
            console.error('上传失败，状态码:', uploadRes.statusCode)
            uni.showToast({ title: '识别失败，请重试', icon: 'none' })
          }
        },
        fail: (err) => {
          uni.hideLoading()
          console.error('上传失败', err)
          uni.showToast({ title: '网络错误，请重试', icon: 'none' })
        },
      })
    },
    fail: (err) => {
      console.error('拍照失败', err)
      uni.showToast({ title: '拍照失败，请重试', icon: 'none' })
    },
  })
}

const isBtnActive = ref(false)
</script>

<template>
  <view class="container">
    <CustomNavbar title="拍照识菜" class="navbar" />

    <view class="decor decor-left" />
    <view class="decor decor-right" />

    <view class="content">
      <view class="tips">
        <text class="tips-title">
          咔嚓一下，美味即来～
        </text>
        <text class="tips-desc">
          拍摄食材照片，为你推荐专属菜谱
        </text>
      </view>

      <view class="camera-container">
        <view class="camera-frame">
          <view class="camera-btn" @click="handleTakePhoto" @touchstart="isBtnActive = true" @touchend="isBtnActive = false">
            <view class="camera-icon">
              <t-icon
                name="camera"
                size="48rpx"
                color="#fff"
              />
            </view>
            <text class="btn-text">
              拍照识别
            </text>
          </view>
        </view>
      </view>

      <view class="helper">
        <text class="helper-text">
          仅支持拍摄单种食材
        </text>
        <text class="helper-text">
          建议光线充足、对焦清晰
        </text>
      </view>
    </view>

    <CustomTabBar />
  </view>
</template>

<style scoped>
/* 基础容器样式 */
.container {
  width: 100vw;
  height: calc(100vh - 90rpx);
  overflow: hidden;
  position: relative;
  display: flex;
  flex-direction: column;
  padding-bottom: env(safe-area-inset-bottom);
}

/* 导航栏样式 */
.navbar {
  position: relative;
  z-index: 10;
}

/* 装饰元素 */
.decor {
  position: absolute;
  width: 200rpx;
  height: 200rpx;
  border-radius: 50%;
  opacity: 0.15;
  z-index: 0;
}

.decor-left {
  top: 20%;
  left: -80rpx;
  background: linear-gradient(135deg, #ff6b8b, #ff8e53);
  animation: float 8s ease-in-out infinite;
}

.decor-right {
  bottom: 30%;
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
  padding: 40rpx 20rpx;
  position: relative;
  z-index: 0;
}

/* 提示文字 */
.tips {
  text-align: center;
  margin-bottom: 80rpx;
}

.tips-title {
  font-size: 36rpx;
  font-weight: 600;
  color: #2d3748;
  display: block;
  margin-bottom: 12rpx;
}

.tips-desc {
  font-size: 24rpx;
  color: #718096;
  line-height: 1.5;
}

/* 拍照容器 */
.camera-container {
  position: relative;
  margin-bottom: 60rpx;
}

/* 相机外框（呼吸效果） */
.camera-frame {
  width: 360rpx;
  height: 360rpx;
  border-radius: 50%;
  background: linear-gradient(135deg, #ff7eb3, #64b5f6);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: relative;
  box-shadow: 0 20rpx 40rpx rgba(100, 181, 246, 0.3);
  animation: breathe 3s ease-in-out infinite;
}

/* 相机按钮 */
.camera-btn {
  width: 320rpx;
  height: 320rpx;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.95);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  position: relative;
  overflow: hidden;
}

/* 按钮点击效果 */
.camera-btn.active {
  transform: scale(0.95);
  box-shadow: 0 10rpx 30rpx rgba(255, 126, 179, 0.2) inset;
}

/* 波纹效果 */
.camera-btn::after {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 100%;
  height: 100%;
  background: rgba(255, 126, 179, 0.1);
  border-radius: 50%;
  transform: translate(-50%, -50%) scale(0);
  transition: transform 0.6s ease-out;
}

.camera-btn.active::after {
  transform: translate(-50%, -50%) scale(1.5);
}

/* 相机图标容器 */
.camera-icon {
  margin-bottom: 24rpx;
  width: 96rpx;
  height: 96rpx;
  border-radius: 50%;
  background: linear-gradient(135deg, #ff7eb3, #64b5f6);
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0 8rpx 16rpx rgba(100, 181, 246, 0.2);
  transition: all 0.3s ease;
}

.camera-btn.active .camera-icon {
  transform: scale(1.1);
  box-shadow: 0 10rpx 20rpx rgba(255, 126, 179, 0.3);
}

/* 按钮文字 */
.btn-text {
  font-size: 30rpx;
  font-weight: 500;
  color: #2d3748;
  letter-spacing: 2rpx;
}

/* 装饰小点 */
.dot {
  position: absolute;
  width: 16rpx;
  height: 16rpx;
  border-radius: 50%;
  background: white;
  opacity: 0.8;
  animation: bounce 4s ease-in-out infinite;
}

.dot-1 {
  top: 20rpx;
  left: 20rpx;
  animation-delay: 0s;
}

.dot-2 {
  top: 20rpx;
  right: 20rpx;
  animation-delay: 1s;
}

.dot-3 {
  bottom: 20rpx;
  left: 20rpx;
  animation-delay: 2s;
}

.dot-4 {
  bottom: 20rpx;
  right: 20rpx;
  animation-delay: 3s;
}

/* 辅助提示文字 */
.helper {
  text-align: center;
  width: 100%;
  max-width: 500rpx;
}

.helper-text {
  font-size: 22rpx;
  color: #a0aec0;
  display: block;
  margin-bottom: 10rpx;
  line-height: 1.4;
}

/* 动画定义 */
/* 呼吸效果 */
@keyframes breathe {
  0%, 100% {
    transform: scale(1);
    opacity: 1;
  }
  50% {
    transform: scale(1.05);
    opacity: 0.9;
  }
}

/* 浮动效果 */
@keyframes float {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-20rpx);
  }
}

/* 弹跳效果 */
@keyframes bounce {
  0%, 100% {
    transform: scale(1);
    opacity: 0.8;
  }
  50% {
    transform: scale(1.3);
    opacity: 1;
  }
}

/* 适配深色模式（可选） */
@media (prefers-color-scheme: dark) {
  .container {
    background-color: #1a202c;
  }

  .tips-title {
    color: #f7fafc;
  }

  .tips-desc, .helper-text {
    color: #a0aec0;
  }

  .camera-btn {
    background: rgba(45, 55, 72, 0.95);
  }

  .btn-text {
    color: #f7fafc;
  }
}
</style>
