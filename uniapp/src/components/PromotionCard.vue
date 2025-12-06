<!-- src/components/PromotionCard.vue -->
<script lang="ts" setup>
import { computed } from 'vue'

interface Props {
  id: number
  title: string
  cover: string
  originalPrice: number
  discountPrice: number
  discount: string // 如 "5折"
  sales: number
  stock: number
  tag: string
  countdown?: {
    hours: number
    minutes: number
    seconds: number
  }
}

const props = defineProps<Props>()

const emit = defineEmits<{
  (e: 'click', id: number): void
}>()

const isSoldOut = computed(() => props.stock <= 0)

// 计算折扣颜色类名
const discountClass = computed(() => {
  const num = Number.parseFloat(props.discount)
  if (num < 5)
    return 'discount-high'
  if (num < 7)
    return 'discount-medium'
  return 'discount-low'
})

function handleClick() {
  emit('click', props.id)
}

// 格式化两位数
function formatNumber(num: number): string {
  return num.toString().padStart(2, '0')
}
</script>

<template>
  <view
    class="promotion-card"
    :class="{ soldout: isSoldOut }"
    @click="handleClick"
  >
    <!-- 商品图片区域 -->
    <view class="promotion-image">
      <image :src="cover" mode="cover" class="goods-img" />

      <!-- 标签 -->
      <view class="promotion-tag">
        <text class="tag-text">
          {{ tag }}
        </text>
      </view>

      <!-- 折扣标签 -->
      <view class="discount-tag" :class="discountClass">
        <text class="discount-text">
          {{ discount }}
        </text>
      </view>

      <!-- 已售罄遮罩 -->
      <view v-if="isSoldOut" class="soldout-overlay">
        <text class="soldout-text">
          已售罄
        </text>
      </view>
    </view>

    <!-- 商品信息 -->
    <view class="promotion-info">
      <text class="promotion-title">
        {{ title }}
      </text>

      <!-- 价格 -->
      <view class="price-container">
        <text class="discount-price">
          ¥{{ discountPrice.toFixed(1) }}
        </text>
        <text class="original-price">
          ¥{{ originalPrice.toFixed(1) }}
        </text>
      </view>

      <!-- 销量 & 库存 -->
      <view class="sales-stock">
        <text class="sales-text">
          已售 {{ sales }}+
        </text>
        <text class="stock-text">
          剩余 {{ stock }} 件
        </text>
      </view>

      <!-- 倒计时 -->
      <view v-if="!isSoldOut && countdown" class="countdown-container">
        <text class="countdown-label">
          限时：
        </text>
        <view class="countdown-box">
          <text class="time-item">
            {{ formatNumber(countdown.hours) }}
          </text>
          <text class="colon">
            :
          </text>
          <text class="time-item">
            {{ formatNumber(countdown.minutes) }}
          </text>
          <text class="colon">
            :
          </text>
          <text class="time-item">
            {{ formatNumber(countdown.seconds) }}
          </text>
        </view>
      </view>
    </view>

    <!-- 操作按钮 -->
    <!-- <view class="promotion-action">
      <button
        class="buy-button"
        :disabled="isSoldOut"
        @click="handleClick"
      >
        <text class="buy-text">{{ isSoldOut ? '已售罄' : '立即抢购' }}</text>
      </button>
    </view> -->
  </view>
</template>

<style scoped>
/* 以下样式直接从原页面复制，保持一致 */
.promotion-card {
  display: flex;
  background-color: white;
  border-radius: 24rpx;
  overflow: hidden;
  box-shadow: 0 8rpx 20rpx rgba(0, 0, 0, 0.05);
  transition: transform 0.3s ease;
}

.promotion-card:active {
  transform: scale(0.98);
}

.promotion-card.soldout {
  opacity: 0.7;
}

.promotion-image {
  width: 200rpx;
  height: 250rpx;
  position: relative;
}

.goods-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.promotion-tag {
  position: absolute;
  top: 12rpx;
  left: 12rpx;
  padding: 6rpx 12rpx;
  border-radius: 16rpx;
  font-size: 20rpx;
  font-weight: 600;
  color: white;
  background-color: #ff5252; /* 默认色，实际可通过 tag 动态绑定，但此处简化 */
}

.discount-tag {
  position: absolute;
  bottom: 12rpx;
  left: 12rpx;
  width: 60rpx;
  height: 60rpx;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 18rpx;
  font-weight: 600;
  background: linear-gradient(135deg, #ff7eb3, #ff5252);
}

.discount-high {
  background: linear-gradient(135deg, #ff4d6d, #ff0054);
}

.discount-medium {
  background: linear-gradient(135deg, #ff7eb3, #ff5252);
}

.discount-low {
  background: linear-gradient(135deg, #ff9f43, #ff7a00);
}

.soldout-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.soldout-text {
  color: white;
  font-size: 28rpx;
  font-weight: 600;
  transform: rotate(-15deg);
  background-color: #ff5252;
  padding: 8rpx 32rpx;
  border-radius: 16rpx;
}

.promotion-info {
  flex: 1;
  padding: 20rpx;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.promotion-title {
  font-size: 26rpx;
  font-weight: 600;
  color: #2d3748;
  margin-bottom: 12rpx;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.price-container {
  display: flex;
  align-items: baseline;
  gap: 12rpx;
  margin-bottom: 12rpx;
}

.discount-price {
  font-size: 32rpx;
  font-weight: 700;
  color: #ff5252;
}

.original-price {
  font-size: 22rpx;
  color: #a0aec0;
  text-decoration: line-through;
}

.sales-stock {
  display: flex;
  justify-content: space-between;
  font-size: 20rpx;
  color: #718096;
  margin-bottom: 12rpx;
  width: 100%;
}

.countdown-container {
  display: flex;
  align-items: center;
  gap: 12rpx;
}

.countdown-label {
  font-size: 22rpx;
  color: #ff5252;
  font-weight: 500;
}

.countdown-box {
  display: flex;
  gap: 6rpx;
}

.time-item {
  width: 36rpx;
  height: 36rpx;
  background-color: #ff5252;
  color: white;
  font-size: 22rpx;
  font-weight: 600;
  border-radius: 8rpx;
  display: flex;
  align-items: center;
  justify-content: center;
}

.colon {
  color: #ff5252;
  font-size: 24rpx;
  font-weight: 600;
}

.promotion-action {
  display: flex;
  align-items: flex-end;
  padding: 20rpx;
}

.buy-button {
  width: 140rpx;
  height: 60rpx;
  background: linear-gradient(135deg, #ff7eb3, #ff5252);
  color: white;
  border: none;
  border-radius: 30rpx;
  font-size: 18rpx;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
}

.buy-button:disabled {
  background-color: #cbd5e0;
  color: #718096;
}

@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.05); }
  100% { transform: scale(1); }
}

.buy-button:not(:disabled) {
  animation: pulse 2s infinite;
}

/* 响应式（保留） */
@media (min-width: 750rpx) {
  .promotion-card {
    flex-direction: column;
    height: 480rpx;
  }

  .promotion-image {
    width: 100%;
    height: 100%;
  }

  .promotion-action {
    align-items: center;
    padding: 16rpx 20rpx;
  }

  .buy-button {
    width: 100%;
  }
}
</style>
