<script lang="ts" setup>
import CustomNavbar from '@/components/CustomNavbar.vue'
import { onLoad } from '@dcloudio/uni-app'
import { onMounted, ref } from 'vue'

// 优惠商品详情数据
const productDetail = ref({
  id: 1,
  title: '番茄买1送1 新鲜直达',
  cover: 'https://picsum.photos/seed/promotion1/800/600',
  images: [
    'https://picsum.photos/seed/promotion1-1/800/600',
    'https://picsum.photos/seed/promotion1-2/800/600',
    'https://picsum.photos/seed/promotion1-3/800/600',
  ],
  originalPrice: 12.9,
  discountPrice: 6.9,
  discount: '5折',
  sales: 1286,
  stock: 324,
  limit: 5, // 限购5件
  startTime: Date.now(),
  endTime: Date.now() + 3600 * 1000 * 8, // 8小时后结束
  tag: '限时特惠',
  brand: '新鲜果蔬',
  category: '蔬菜',
  origin: '山东寿光',
  description: '精选新鲜番茄，自然成熟，酸甜多汁，营养丰富。现在参与买1送1活动，数量有限，先到先得！',
  details: [
    '规格：500g/份（买1送1，实发2份）',
    '产地：山东寿光蔬菜基地',
    '保质期：7天（冷藏保存）',
    '配送：全国顺丰包邮',
    '特点：自然成熟，无催熟剂，酸甜可口',
  ],
  service: [
    '坏果包赔',
    '次日达',
    '7天无理由退换',
    '专业保鲜包装',
  ],
})

// 状态管理
const countdown = ref({ hours: 0, minutes: 0, seconds: 0 })
const timer = ref<NodeJS.Timeout | null>(null)
const buyCount = ref(1)
// const activeImageIndex = ref(0)
const isSoldOut = ref(false)

// 页面加载
onLoad((options) => {
  const id = options?.id
  console.log('优惠商品ID:', id)

  // 实际项目中这里应该根据id请求真实的商品详情数据
  // 模拟库存判断
  isSoldOut.value = productDetail.value.stock <= 0

  // 初始化倒计时
  calculateCountdown()
})

// 计算倒计时
function calculateCountdown() {
  const now = Date.now()
  const diff = productDetail.value.endTime - now

  if (diff <= 0) {
    countdown.value = { hours: 0, minutes: 0, seconds: 0 }
    isSoldOut.value = true
    return
  }

  countdown.value = {
    hours: Math.floor(diff / 3600000),
    minutes: Math.floor((diff % 3600000) / 60000),
    seconds: Math.floor((diff % 60000) / 1000),
  }
}

// 格式化数字为两位数
function formatNumber(num: number) {
  return num.toString().padStart(2, '0')
}

// 启动倒计时
onMounted(() => {
  // 每秒更新一次
  timer.value = setInterval(() => {
    calculateCountdown()
  }, 1000)

  return () => {
    if (timer.value)
      clearInterval(timer.value)
  }
})

// // 切换商品图片
// function switchImage(index: number) {
//   activeImageIndex.value = index
// }

// // 增减购买数量
// function changeCount(type: 'add' | 'reduce') {
//   if (type === 'add') {
//     if (buyCount.value < productDetail.value.limit && buyCount.value < productDetail.value.stock) {
//       buyCount.value++
//     }
//     else {
//       uni.showToast({
//         title: `最多可购买${Math.min(productDetail.value.limit, productDetail.value.stock)}件`,
//         icon: 'none',
//       })
//     }
//   }
//   else {
//     if (buyCount.value > 1) {
//       buyCount.value--
//     }
//   }
// }

// // 直接输入购买数量
// function inputCount(value: string) {
//   const num = Number.parseInt(value)
//   if (isNaN(num) || num < 1) {
//     buyCount.value = 1
//   }
//   else {
//     buyCount.value = Math.min(num, productDetail.value.limit, productDetail.value.stock)
//   }
// }

// 立即购买
function buyNow() {
  if (isSoldOut.value) {
    uni.showToast({ title: '活动已结束或商品已售罄', icon: 'none' })
    return
  }

  // 实际项目中跳转到下单页面
  uni.showToast({ title: `已选择${buyCount.value}件，准备下单`, icon: 'success' })
  console.log('立即购买:', {
    productId: productDetail.value.id,
    count: buyCount.value,
    totalPrice: productDetail.value.discountPrice * buyCount.value,
  })

  // 跳转到下单页面
  // uni.navigateTo({
  //   url: `/pages/order/confirm?id=${productDetail.value.id}&count=${buyCount.value}`
  // })
}

// 加入购物车
function addToCart() {
  if (isSoldOut.value) {
    uni.showToast({ title: '活动已结束或商品已售罄', icon: 'none' })
    return
  }

  uni.showToast({ title: '已加入购物车', icon: 'success' })
  console.log('加入购物车:', {
    productId: productDetail.value.id,
    count: buyCount.value,
    price: productDetail.value.discountPrice,
  })
}

// 获取标签样式
function getTagClass() {
  switch (productDetail.value.tag) {
    case '限时特惠':
      return 'tag-limited'
    case '热销推荐':
      return 'tag-hot'
    case '新人专享':
      return 'tag-new'
    case '爆款推荐':
      return 'tag-bestseller'
    default:
      return ''
  }
}

// 计算节省金额
function calculateSaveAmount() {
  return (productDetail.value.originalPrice - productDetail.value.discountPrice) * buyCount.value
}
</script>

<template>
  <view class="promotion-detail-container">
    <!-- 导航栏 -->
    <CustomNavbar title="优惠详情" :show-back="true" />

    <!-- 商品图片轮播 -->
    <view class="product-swiper">
      <view
        class="cover-img"
        :style="{ backgroundImage: `url(${productDetail.cover})` }"
      />
    </view>

    <!-- 商品信息 -->
    <view class="product-info">
      <view style="display: flex; flex-direction: column;">
        <view style="margin-bottom: 10rpx; display: flex; gap: 8rpx;">
          <text class="tag" :class="getTagClass()">
            {{ productDetail.discount }}
          </text>
          <text class="tag" :class="getTagClass()">
            {{ productDetail.tag }}
          </text>
        </view>
        <text class="product-title">
          {{ productDetail.title }}
        </text>
      </view>

      <!-- 价格信息 -->
      <view class="price-container">
        <text class="discount-price">
          ¥{{ productDetail.discountPrice.toFixed(1) }}
        </text>
        <text class="original-price">
          ¥{{ productDetail.originalPrice.toFixed(1) }}
        </text>
        <text class="save-amount">
          省 ¥{{ calculateSaveAmount().toFixed(1) }}
        </text>
      </view>

      <!-- 销量和库存 -->
      <view class="sales-stock">
        <text class="sales-text">
          已售 {{ productDetail.sales }}+
        </text>
        <text class="stock-text">
          剩余 {{ productDetail.stock }} 件
        </text>
      </view>

      <!-- 倒计时 -->
      <view v-if="!isSoldOut" class="countdown-container">
        <text class="countdown-label">
          活动剩余：
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
        <text class="countdown-desc">
          倒计时结束恢复原价
        </text>
      </view>

      <!-- 已售罄提示 -->
      <view v-if="isSoldOut" class="soldout-label">
        <text class="soldout-text">
          活动已结束
        </text>
      </view>
    </view>

    <!-- 商品详情 -->
    <scroll-view class="detail-scroll" scroll-y>
      <!-- 商品基本信息 -->
      <view class="product-base-info">
        <view class="info-item">
          <text class="info-label">
            品牌：
          </text>
          <text class="info-value">
            {{ productDetail.brand }}
          </text>
        </view>
        <view class="info-item">
          <text class="info-label">
            分类：
          </text>
          <text class="info-value">
            {{ productDetail.category }}
          </text>
        </view>
        <view class="info-item">
          <text class="info-label">
            产地：
          </text>
          <text class="info-value">
            {{ productDetail.origin }}
          </text>
        </view>
        <view class="info-item">
          <text class="info-label">
            限购：
          </text>
          <text class="info-value">
            每人限 {{ productDetail.limit }} 件
          </text>
        </view>
      </view>

      <!-- 商品描述 -->
      <view class="product-description">
        <text class="section-title">
          商品描述
        </text>
        <text class="desc-text">
          {{ productDetail.description }}
        </text>
      </view>

      <!-- 商品详情 -->
      <view class="product-details">
        <text class="section-title">
          商品详情
        </text>
        <view class="details-list">
          <view v-for="(detail, index) in productDetail.details" :key="index" class="detail-item">
            <text class="detail-icon">
              ✅
            </text>
            <text class="detail-text">
              {{ detail }}
            </text>
          </view>
        </view>
      </view>

      <!-- 服务保障 -->
      <view class="service-guarantee">
        <text class="section-title">
          服务保障
        </text>
        <view class="service-list">
          <view v-for="(service, index) in productDetail.service" :key="index" class="service-item">
            <text class="service-icon">
              🛡️
            </text>
            <text class="service-text">
              {{ service }}
            </text>
          </view>
        </view>
      </view>
    </scroll-view>

    <!-- 底部购买栏 -->
    <view class="buy-bar">
      <view class="buy-buttons">
        <button class="cart-btn" :disabled="isSoldOut" @click="addToCart">
          <text class="cart-icon">
            🛒
          </text>
          <text class="cart-text">
            加入收藏
          </text>
        </button>
        <button class="buy-now-btn" :disabled="isSoldOut" @click="buyNow">
          <text class="buy-text">
            立即抢购
          </text>
          <text class="total-price">
            ¥{{ (productDetail.discountPrice * buyCount).toFixed(1) }}
          </text>
        </button>
      </view>
    </view>

    <view style="height: 120rpx;" />
  </view>
</template>

<style scoped>
/* 基础容器样式 */
.promotion-detail-container {
  width: 100%;
  padding-bottom: env(safe-area-inset-bottom);
}

/* 商品图片轮播 */
.product-swiper {
  position: relative;
  padding-top: 0; /* 适配导航栏高度 */
}

.cover-img {
  width: 100vw;
  height: 250rpx;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

/* 商品标签 */
.product-tags {
  position: absolute;
  top: calc(var(--custom-navbar-height, 44px) + 20rpx);
  left: 20rpx;
  display: flex;
  gap: 12rpx;
}

.tag {
  padding: 8rpx 16rpx;
  border-radius: 16rpx;
  font-size: 16rpx;
  font-weight: 600;
  color: white;
  display: inline;
}

.tag-limited {
  background-color: #ff5252;
}

.tag-hot {
  background-color: #ff9f43;
}

.tag-new {
  background-color: #4cd964;
}

.tag-bestseller {
  background-color: #64b5f6;
}

.discount-tag {
  width: 80rpx;
  height: 80rpx;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 28rpx;
  font-weight: 700;
  background: linear-gradient(135deg, #ff7eb3, #ff5252);
  position: absolute;
  top: calc(var(--custom-navbar-height, 44px) + 20rpx);
  right: 20rpx;
}

/* 商品信息 */
.product-info {
  padding: 20rpx;
  background-color: white;
  border-bottom: 1rpx solid #f0f2f5;
}

.product-title {
  font-size: 32rpx;
  font-weight: 600;
  color: #2d3748;
  display: block;
  line-height: 1.5;
}

/* 价格信息 */
.price-container {
  display: flex;
  align-items: baseline;
  gap: 16rpx;
  margin-bottom: 16rpx;
}

.discount-price {
  font-size: 40rpx;
  font-weight: 700;
  color: #ff5252;
}

.original-price {
  font-size: 24rpx;
  color: #a0aec0;
  text-decoration: line-through;
}

.save-amount {
  font-size: 24rpx;
  color: #4cd964;
  background-color: #f0fdf4;
  padding: 4rpx 12rpx;
  border-radius: 16rpx;
}

/* 销量和库存 */
.sales-stock {
  display: flex;
  justify-content: space-between;
  font-size: 22rpx;
  color: #718096;
  margin-bottom: 16rpx;
}

/* 倒计时 */
.countdown-container {
  display: flex;
  align-items: center;
  gap: 12rpx;
  background-color: #fff5f5;
  border-radius: 16rpx;
  padding: 12rpx;
}

.countdown-label {
  font-size: 24rpx;
  color: #ff5252;
  font-weight: 500;
}

.countdown-box {
  display: flex;
  gap: 8rpx;
}

.time-item {
  width: 44rpx;
  height: 44rpx;
  background-color: #ff5252;
  color: white;
  font-size: 24rpx;
  font-weight: 700;
  border-radius: 8rpx;
  display: flex;
  align-items: center;
  justify-content: center;
}

.colon {
  color: #ff5252;
  font-size: 28rpx;
  font-weight: 700;
}

.countdown-desc {
  font-size: 22rpx;
  color: #ff5252;
  margin-left: 8rpx;
}

/* 已售罄提示 */
.soldout-label {
  background-color: #f5f5f5;
  border-radius: 16rpx;
  padding: 12rpx;
  text-align: center;
}

.soldout-text {
  font-size: 24rpx;
  color: #a0aec0;
  font-weight: 500;
}

/* 详情滚动区域 */
.detail-scroll {
  width: 100%;
}

/* 商品基本信息 */
.product-base-info {
  background-color: white;
  border-radius: 24rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
  margin: 16rpx;
}

.info-item {
  display: flex;
  margin-bottom: 16rpx;
}

.info-item:last-child {
  margin-bottom: 0;
}

.info-label {
  font-size: 24rpx;
  color: #718096;
  width: 120rpx;
}

.info-value {
  font-size: 24rpx;
  color: #2d3748;
  flex: 1;
}

/* 商品描述 */
.product-description {
  background-color: white;
  border-radius: 24rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
  margin: 16rpx;
}

.section-title {
  font-size: 28rpx;
  font-weight: 600;
  color: #2d3748;
  margin-bottom: 16rpx;
  display: block;
  position: relative;
  padding-left: 16rpx;
}

.section-title::before {
  content: '';
  position: absolute;
  left: 0;
  top: 8rpx;
  width: 8rpx;
  height: 24rpx;
  background: linear-gradient(135deg, #ff7eb3, #64b5f6);
  border-radius: 4rpx;
}

.desc-text {
  font-size: 24rpx;
  color: #4a5568;
  line-height: 1.8;
}

/* 商品详情列表 */
.product-details {
  background-color: white;
  border-radius: 24rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
  margin: 16rpx;
}

.details-list {
  display: flex;
  flex-direction: column;
  gap: 16rpx;
}

.detail-item {
  display: flex;
  align-items: flex-start;
  gap: 12rpx;
}

.detail-icon {
  font-size: 24rpx;
  color: #4cd964;
  margin-top: 4rpx;
}

.detail-text {
  font-size: 24rpx;
  color: #4a5568;
  line-height: 1.6;
  flex: 1;
}

/* 服务保障 */
.service-guarantee {
  background-color: white;
  border-radius: 24rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
  margin: 16rpx;
}

.service-list {
  display: flex;
  flex-wrap: wrap;
  gap: 16rpx;
}

.service-item {
  display: flex;
  align-items: center;
  gap: 8rpx;
  background-color: #f5f6f7;
  padding: 12rpx 20rpx;
  border-radius: 16rpx;
}

.service-icon {
  font-size: 24rpx;
  color: #64b5f6;
}

.service-text {
  font-size: 22rpx;
  color: #4a5568;
}

/* 底部购买栏 */
.buy-bar {
  position: fixed;
  bottom: env(safe-area-inset-bottom);
  left: 0;
  right: 0;
  background-color: white;
  padding: 16rpx 20rpx;
  border-top: 1rpx solid #f0f2f5;
  display: flex;
  align-items: center;
  gap: 16rpx;
  z-index: 998;
}

.count-selector {
  display: flex;
  align-items: center;
  gap: 12rpx;
}

.count-label {
  font-size: 24rpx;
  color: #2d3748;
  font-weight: 500;
}

.count-controls {
  display: flex;
  align-items: center;
  border: 1rpx solid #e2e8f0;
  border-radius: 12rpx;
  overflow: hidden;
}

.control-btn {
  width: 60rpx;
  height: 60rpx;
  background-color: #f9fafb;
  color: #4a5568;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
}

.control-btn:disabled {
  background-color: #f3f4f6;
  color: #cbd5e0;
}

.control-icon {
  font-size: 32rpx;
}

.count-input {
  width: 80rpx;
  height: 60rpx;
  text-align: center;
  font-size: 24rpx;
  color: #2d3748;
  border-left: 1rpx solid #e2e8f0;
  border-right: 1rpx solid #e2e8f0;
}

.buy-buttons {
  flex: 1;
  display: flex;
  gap: 16rpx;
}

.cart-btn {
  flex: 1;
  background-color: #f5f6f7;
  color: #4a5568;
  border: none;
  border-radius: 32rpx;
  font-size: 24rpx;
  font-weight: 500;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8rpx;
  height: 80rpx;
}

.cart-icon {
  font-size: 28rpx;
}

.buy-now-btn {
  flex: 1.5;
  background: linear-gradient(135deg, #ff7eb3, #ff5252);
  color: white;
  border: none;
  border-radius: 32rpx;
  font-size: 26rpx;
  font-weight: 600;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12rpx;
  height: 80rpx;
  animation: pulse 2s infinite;
}

.buy-now-btn:disabled {
  background-color: #cbd5e0;
  color: #718096;
  animation: none;
}

.total-price {
  font-size: 28rpx;
  font-weight: 700;
}

/* 动画效果 */
@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.03); }
  100% { transform: scale(1); }
}
</style>
