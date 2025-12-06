<script lang="ts" setup>
import CustomNavbar from '@/components/CustomNavbar.vue'
import { request } from '@/utils'
import { onLoad, onUnload } from '@dcloudio/uni-app'
import { ref } from 'vue'

const loading = ref(true)
const emptyState = ref(false)
const resultData = ref<any>({
  imagePath: '', // 临时图片路径
  predictResult: null, // 识别结果
})

// 页面加载：读取缓存的图片+识别结果
onLoad(() => {
  request.Get<IResult<any>>('/api/v1/access/captcha').then(({ data }) => {
    console.log('加载缓存数据:', data)
  })
  try {
    const cacheData = uni.getStorageSync('latestPredictionData')
    if (cacheData && cacheData.imagePath && cacheData.predictResult?.success) {
      resultData.value = cacheData
      // 格式化置信度（转为百分比，限制最大100%）
      resultData.value.predictResult.top_k_predictions.forEach((item: any) => {
        item.confidence = Math.min(Math.round(item.probability * 100) / 10, 100)
      })
      console.log('加载识别结果:', resultData.value)
    }
    else {
      emptyState.value = true
      uni.showToast({ title: '未找到有效识别数据', icon: 'none' })
    }
  }
  catch (e) {
    console.error('读取缓存失败:', e)
    emptyState.value = true
    uni.showToast({ title: '数据加载失败', icon: 'none' })
  }
  finally {
    loading.value = false
  }
})

// 页面卸载：清除缓存
onUnload(() => {
  uni.removeStorageSync('latestPredictionData')
})

// 重新拍照
function reTakePhoto() {
  uni.switchTab({
    url: '/pages/camera/index',
  })
}

// 置信度进度条样式（根据置信度变色）
function getProgressStyle(confidence: number) {
  let color = '#64b5f6' // 蓝色（中等置信度）
  if (confidence >= 80)
    color = '#4cd964' // 绿色（高置信度）
  if (confidence < 50)
    color = '#ff9f43' // 橙色（低置信度）
  return {
    width: `${confidence}%`,
    background: color,
  }
}

function getCookBook() {
  // 跳转到获取菜谱页面，传入蔬菜名称
  // uni.navigateTo({
  //   url: '/pages/cookbook/clist',
  // })

  const vegetable = resultData.value.predictResult?.predicted_class
  if (!vegetable) {
    uni.showToast({ title: '未识别到食材', icon: 'none' })
    return
  }
  // 使用 navigateTo 跳转并传参（注意：不能用 switchTab 传参）
  uni.navigateTo({
    url: `/pages/cookbook/clist?vegetable=${encodeURIComponent(vegetable)}`,
  })
}
</script>

<template>
  <view class="result-container">
    <CustomNavbar title="识别结果" :show-back="true" />

    <!-- 加载中状态 -->
    <view v-if="loading" class="loading-state">
      <text class="loading-icon">
        ⏳
      </text>
      <text class="loading-text">
        加载识别结果中...
      </text>
    </view>

    <!-- 空状态/失败状态 -->
    <view v-if="emptyState && !loading" class="empty-state">
      <text class="empty-icon">
        📷
      </text>
      <text class="empty-text">
        识别失败或无有效数据
      </text>
      <button class="retry-btn" @click="reTakePhoto()">
        重新拍照
      </button>
    </view>

    <!-- 识别结果内容（成功状态） -->
    <!-- 关键修复1：给 scroll-view 加 flex:1 让其自适应父容器，避免宽度溢出 -->
    <scroll-view v-if="!loading && !emptyState" class="result-content" scroll-y>
      <!-- 拍摄图片展示 -->
      <view class="image-section">
        <text class="section-title">
          拍摄食材
        </text>
        <view class="image-container">
          <image :src="resultData.imagePath" mode="aspectFill" class="taken-image" />
        </view>
      </view>

      <!-- 识别结果（TOP1） -->
      <view class="top1-result">
        <text class="section-title">
          最可能的食材
        </text>
        <view class="top1-card">
          <view class="top1-icon">
            <text class="icon-text">
              {{ resultData.predictResult.predicted_class.charAt(0) }}
            </text>
          </view>
          <view class="top1-info">
            <text class="ingredient-name">
              {{ resultData.predictResult.predicted_class }}
            </text>
            <view class="confidence-container top1-confidence">
              <view class="progress-bg">
                <view class="progress-bar" :style="getProgressStyle(resultData.predictResult.top_k_predictions[0].confidence)" />
              </view>
              <text class="confidence-text">
                {{ resultData.predictResult.top_k_predictions[0].confidence }}%
              </text>
            </view>
          </view>
        </view>
      </view>

      <!-- 其他可能结果（TOP2-TOP3） -->
      <view v-if="resultData.predictResult.top_k_predictions.length > 1" class="other-results">
        <text class="section-title">
          其他可能的食材
        </text>
        <view class="other-list">
          <view v-for="(item, index) in resultData.predictResult.top_k_predictions.slice(1)" :key="index" class="other-item">
            <view class="other-icon">
              <text class="icon-text">
                {{ item.class.charAt(0) }}
              </text>
            </view>
            <view class="other-info">
              <text class="other-name">
                {{ item.class }}
              </text>
              <view class="confidence-container">
                <view class="progress-bg">
                  <view class="progress-bar" :style="getProgressStyle(item.confidence)" />
                </view>
                <text class="confidence-text">
                  {{ item.confidence }}%
                </text>
              </view>
            </view>
          </view>
        </view>
      </view>

      <!-- 底部操作栏 -->
      <view class="bottom-actions">
        <t-button block @click="reTakePhoto()">
          重新拍照
        </t-button>
        <t-button block theme="primary" @click="getCookBook()">
          菜谱获取
        </t-button>
      </view>
    </scroll-view>
  </view>
</template>

<style scoped>
/* 基础容器 */
.result-container {
  width: 100%; /* 用 viewport 宽度，比 100% 更稳定 */
  padding-bottom: env(safe-area-inset-bottom);
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* 加载状态 */
.loading-state {
  text-align: center;
  padding: 200rpx 0;
}

.loading-icon {
  font-size: 80rpx;
  margin-bottom: 30rpx;
  display: block;
  color: #ff7eb3;
}

.loading-text {
  font-size: 28rpx;
  color: #a0aec0;
}

/* 空状态 */
.empty-state {
  text-align: center;
  padding: 200rpx 0;
}

.empty-icon {
  font-size: 80rpx;
  margin-bottom: 30rpx;
  display: block;
  color: #a0aec0;
}

.empty-text {
  font-size: 28rpx;
  color: #718096;
  margin-bottom: 40rpx;
  display: block;
}

.retry-btn {
  background: linear-gradient(135deg, #ff7eb3, #64b5f6);
  color: white;
  border: none;
  border-radius: 32rpx;
  font-size: 24rpx;
  padding: 0 40rpx;
  height: 64rpx;
  line-height: 64rpx;
}

/* 结果内容滚动区 - 关键修复3 */
.result-content {
  flex: 1; /* 让 scroll-view 自适应父容器高度，避免滚动异常 */
  width: 100%;
  /* padding: 24rpx; */
  overflow-x: hidden; /* 强制隐藏水平滚动条，避免偏移视觉 */
}

/* 通用区域标题 */
.section-title {
  font-size: 28rpx;
  font-weight: 600;
  color: #2d3748;
  display: block;
  margin-bottom: 16rpx;
}

/* 拍摄图片展示区 */
.image-section {
  background-color: white;
  border-radius: 24rpx;
  padding: 24rpx;
  margin-bottom: 24rpx;
  /* box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05); */
}

.image-container {
  position: relative;
  border-radius: 16rpx;
  overflow: hidden;
}

.taken-image {
  width: 100%;
  height: 300rpx;
  object-fit: cover;
  border-radius: 16rpx;
}

/* TOP1 识别结果 */
.top1-result {
  background-color: white;
  border-radius: 24rpx;
  padding: 24rpx;
  margin-bottom: 24rpx;
  /* box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05); */
}

.top1-card {
  display: flex;
  align-items: center;
  gap: 24rpx;
  padding: 16rpx 0;
  /* 关键修复4：避免 flex 布局溢出 */
  flex-wrap: wrap;
}

.top1-icon {
  width: 120rpx;
  height: 120rpx;
  border-radius: 16rpx;
  background: linear-gradient(135deg, #ff7eb3, #64b5f6);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 64rpx;
  font-weight: 700;
  box-shadow: 0 8rpx 16rpx rgba(100, 181, 246, 0.2);
}

.top1-info {
  flex: 1;
  min-width: 0; /* 关键修复5：解决 flex 子元素文本溢出导致的父容器宽度异常 */
  display: flex;
  flex-direction: column;
  gap: 16rpx;
}

.ingredient-name {
  font-size: 32rpx;
  font-weight: 600;
  color: #2d3748;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis; /* 防止食材名称过长导致溢出 */
}

.top1-confidence {
  width: 100%;
}

.confidence-container {
  display: flex;
  align-items: center;
  gap: 12rpx;
  flex-wrap: wrap; /* 防止小屏幕下布局溢出 */
}

.confidence-text {
  font-size: 22rpx;
  color: #ff7eb3;
  font-weight: 600;
  width: 180rpx;
  text-align: right;
}

.progress-bg {
  flex: 1;
  min-width: 120rpx; /* 小屏幕下进度条最小宽度，避免过窄 */
  height: 14rpx;
  background-color: #f0f2f5;
  border-radius: 7rpx;
  overflow: hidden;
}

.progress-bar {
  height: 100%;
  border-radius: 7rpx;
  transition: width 0.5s ease;
}

.recipe-btn {
  background: linear-gradient(135deg, #ff7eb3, #64b5f6);
  color: white;
  border: none;
  border-radius: 16rpx;
  font-size: 22rpx;
  padding: 12rpx 0;
  font-weight: 500;
  width: 100%; /* 按钮占满宽度，避免布局错乱 */
}

/* 其他可能结果 */
.other-results {
  background-color: white;
  border-radius: 24rpx;
  padding: 24rpx;
  margin-bottom: 24rpx;
  /* box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05); */
}

.other-list {
  display: flex;
  flex-direction: column;
  gap: 16rpx;
}

.other-item {
  display: flex;
  align-items: center;
  gap: 20rpx;
  padding: 16rpx;
  border: 1rpx solid #f0f2f5;
  border-radius: 16rpx;
  transition: all 0.3s ease;
  flex-wrap: wrap; /* 关键修复6：小屏幕下自动换行 */
}

.other-item:active {
  background-color: rgba(100, 181, 246, 0.05);
}

.other-icon {
  width: 90rpx;
  height: 90rpx;
  border-radius: 12rpx;
  background: linear-gradient(135deg, #64b5f6, #4cd964);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 40rpx;
  font-weight: 600;
  box-shadow: 0 4rpx 8rpx rgba(64, 217, 100, 0.15);
}

.other-info {
  flex: 1;
  min-width: 0; /* 防止文本溢出 */
  display: flex;
  flex-direction: column;
  gap: 8rpx;
}

.other-name {
  font-size: 24rpx;
  font-weight: 500;
  color: #2d3748;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.other-recipe-btn {
  background-color: #f5f6f7;
  color: #64b5f6;
  border: none;
  border-radius: 12rpx;
  font-size: 20rpx;
  padding: 8rpx 16rpx;
  /* 关键修复7：按钮固定尺寸，避免布局跳动 */
  min-width: 100rpx;
  text-align: center;
}

/* 底部操作栏 */
.bottom-actions {
  display: flex;
  width: 100%;
  padding: 16rpx;
  gap: 16rpx;
  position: fixed;
  bottom: 0;
  left: 0;
  background-color: white;
}

.rephoto-btn {
  flex: 1;
  background-color: #f5f6f7;
  color: #4a5568;
  border: none;
  border-radius: 32rpx;
  font-size: 24rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12rpx;
  height: 80rpx;
}

/* 动画效果 */
@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.03); }
  100% { transform: scale(1); }
}
</style>
