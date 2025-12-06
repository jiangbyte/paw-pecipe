<script lang="ts" setup>
import { useBizCartApi, useBizProductSkuApi } from '@/api'
import CustomNavbar from '@/components/CustomNavbar.vue'
import { onLoad } from '@dcloudio/uni-app'
import { computed, ref } from 'vue'

const productDetail = ref()
const isSoldOut = ref(false)

const cardForm = ref({
  skuId: '',
  quantity: 1,
  productId: '',
  specId: '',
})
// 页面加载
onLoad((options) => {
  const id = options?.id as string
  cardForm.value.skuId = id
  console.log('优惠商品ID:', id)

  useBizProductSkuApi().GetBizProductSkuWithProduct(id).then(({ data, success }) => {
    if (success) {
      productDetail.value = data
      console.log(data)
      cardForm.value.productId = data?.product.id
    }
  }).catch((err) => {
    console.log(err)
    console.error('加载更多失败:', err)
  }).finally(() => {
  })

  // // 实际项目中这里应该根据id请求真实的商品详情数据
  // // 模拟库存判断
  // isSoldOut.value = productDetail.value.stock <= 0

  // // 初始化倒计时
  // calculateCountdown()
})

// 立即购买
function buyNow() {
  if (isSoldOut.value) {
    uni.showToast({ title: '活动已结束或商品已售罄', icon: 'none' })
    return
  }

  // 实际项目中跳转到下单页面
  uni.showToast({ title: `已选择${cardForm.value.quantity}件，准备下单`, icon: 'success' })
  console.log('立即购买:', {
    productId: productDetail.value.id,
    count: cardForm.value.quantity,
    totalPrice: productDetail.value.discountPrice * cardForm.value.quantity,
  })

  // 跳转到下单页面
  // uni.navigateTo({
  //   url: `/pages/order/confirm?id=${productDetail.value.id}&count=${buyCount.value}`
  // })
}

// 加入购物车
function addToCart() {
  console.log('购物车:', cardForm.value)
  // 售罄判断
  if (isSoldOut.value) {
    uni.showToast({ title: '活动已结束或商品已售罄', icon: 'none' })
  }
  // 商品ID
  if (!cardForm.value.productId) {
    uni.showToast({ title: '商品ID不能为空', icon: 'none' })
    return
  }
  // skuID
  if (!cardForm.value.skuId) {
    uni.showToast({ title: 'skuID不能为空', icon: 'none' })
    return
  }
  // 数量
  if (!cardForm.value.quantity) {
    uni.showToast({ title: '数量不能为空', icon: 'none' })
    return
  }

  console.log('加入购物车:', cardForm.value)
  useBizCartApi().AddToCart(cardForm.value).then(({ data, success }) => {
    if (success) {
      uni.showToast({ title: '加入购物车成功', icon: 'none' })
    }
  }).catch((err) => {
    console.log(err)
  }).finally(() => {
  })
}

// 获取标签样式
function getTagClass(v: number) {
  switch (v) {
    case 1:
      return 'tag-limited'
    case 2:
      return 'tag-hot'
    case 3:
      return 'tag-new'
    case 4:
      return 'tag-bestseller'
    default:
      return ''
  }
}

// 计算节省金额
function calculateSaveAmount() {
  return (productDetail.value?.originalPrice - productDetail.value?.discountPrice) * cardForm.value.quantity
}

const specesPrice = ref(0)
function onChange(e: { value: string }) {
  cardForm.value.specId = e.value
  // 获取价格
  specesPrice.value = productDetail.value?.specs.find((item: { id: any }) => item.id === e.value)?.price.toFixed(2)
}
function handleCountChange(val: { value: number }) {
  cardForm.value.quantity = val.value
}

const totalPrice = computed(() => {
  return cardForm.value.quantity * specesPrice.value
})
</script>

<template>
  <view class="promotion-detail-container">
    <!-- 导航栏 -->
    <CustomNavbar title="食材详情" :show-back="true" />

    <!-- 商品图片轮播 -->
    <view class="product-swiper">
      <!-- <view
        class="cover-img"
        :style="{ backgroundImage: `url(${productDetail?.product.cover})` }"
      /> -->
      <swiper
        indicator-dots
        indicator-color="rgba(255,255,255,0.5)"
        indicator-active-color="#ff7eb3"
        circular
        autoplay
        interval="3000"
      >
        <swiper-item v-for="(img, index) in productDetail?.product.images" :key="index">
          <image :src="img" mode="widthFix" class="swiper-img" />
        </swiper-item>
      </swiper>
    </view>

    <!-- 商品信息 -->
    <view class="product-info">
      <view style="display: flex; flex-direction: column;">
        <view style="margin-bottom: 10rpx; display: flex; gap: 8rpx;">
          <text class="tag" :class="getTagClass(1)">
            {{ productDetail?.discount }}
          </text>
          <text v-for="item in productDetail?.tags" :key="item" class="tag" :class="getTagClass(2)">
            {{ item }}
          </text>
        </view>
        <text class="product-title">
          {{ productDetail?.product.title }}
        </text>
      </view>

      <!-- 价格信息 -->
      <view class="price-container">
        <text class="discount-price">
          ¥{{ productDetail?.discountPrice.toFixed(2) }}
        </text>
        <text class="original-price">
          ¥{{ productDetail?.originalPrice.toFixed(2) }}
        </text>
        <text class="save-amount">
          省 ¥{{ calculateSaveAmount().toFixed(2) }}
        </text>
      </view>

      <!-- 销量和库存 -->
      <view class="sales-stock">
        <text class="sales-text">
          已售 0 件+
        </text>
      </view>

      <!-- 库存状态 -->
      <view class="stock-container">
        <text class="stock-icon">
          📦
        </text>
        <text class="stock-text">
          {{ isSoldOut ? '已售罄' : `库存剩余 ${productDetail?.totalStock} 件` }}
        </text>
      </view>

      <!-- 规格选择 -->
      <t-radio-group
        allow-uncheck
        t-class="horizontal-box"
        class="horizontal-box"
        :custom-style="{
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
          flexWrap: 'wrap',
          margin: '8px',
        }"
        @change="onChange"
      >
        <view v-for="spec in productDetail?.specs" :key="spec.id" :class="`card ${cardForm.specId === spec.id ? 'card--active' : ''}`">
          <t-icon
            v-if="cardForm.specId === spec.id"
            name="check"
            t-class="card__icon"
            class="card__icon"
            custom-style="font-size: 12px;"
          />
          <t-radio icon="none" :value="spec.id" borderless :disabled="spec.stock <= 0">
            <text class="spec-name">
              {{ spec.name }}
            </text>
            <text class="spec-price">
              ¥{{ spec.price.toFixed(2) }}
            </text>
            <text v-if="spec.stock <= 0" class="spec-stock">
              已售罄
            </text>
          </t-radio>
        </view>
      </t-radio-group>

      <view class="count-selector">
        <text class="count-label">
          购买数量：
        </text>
        <t-stepper
          :max="productDetail?.limitPerUser"
          :min="1"
          theme="filled"
          @change="handleCountChange"
        />
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
            {{ productDetail?.product.brand }}
          </text>
        </view>
        <view class="info-item">
          <text class="info-label">
            分类：
          </text>
          <text class="info-value">
            {{ productDetail?.product.categoryName }}
          </text>
        </view>
        <view class="info-item">
          <text class="info-label">
            产地：
          </text>
          <text class="info-value">
            {{ productDetail?.product.origin }}
          </text>
        </view>
        <view class="info-item">
          <text class="info-label">
            限购：
          </text>
          <text class="info-value">
            每人限 {{ productDetail?.limitPerUser }} 件
          </text>
        </view>
      </view>

      <!-- 商品描述 -->
      <view class="product-description">
        <text class="section-title">
          商品描述
        </text>
        <text class="desc-text">
          {{ productDetail?.product.description }}
        </text>
      </view>

      <!-- 商品详情 -->
      <view class="product-details">
        <text class="section-title">
          商品详情
        </text>
        <view class="details-list">
          <view v-for="(detail, index) in productDetail?.product.details" :key="index" class="detail-item">
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
          <view v-for="(service, index) in productDetail?.product.service" :key="index" class="service-item">
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
        <t-button :disabled="isSoldOut" @click="addToCart">
          加入购物车
        </t-button>
        <t-button theme="danger" :disabled="isSoldOut" @click="buyNow">
          立即下单 ¥{{ totalPrice.toFixed(2) }}
        </t-button>
      </view>
    </view>

    <view style="height: 120rpx;" />
  </view>
</template>

<style scoped>
.card {
    position: relative;
    margin: 24rpx;
    border-radius: 12rpx;
    overflow: hidden;
    box-sizing: border-box;
    border: 3rpx solid var(--td-bg-color-container, #fff);
    border-color: #dddddd
}

.card--active {
    border-color: var(--td-brand-color, #0052d9);
}

.card--active::after {
    content: '';
    display: block;
    position: absolute;
    left: 0;
    top: 0;
    width: 0;
    border-width: 28px 28px 28px 0;
    border-style: solid;
    border-color: var(--td-brand-color, #0052d9) transparent transparent transparent;
}

.card__icon {
    color: var(--td-bg-color-container, #fff);
    position: absolute;
    left: 1.5px;
    top: 1.5px;
    z-index: 1;
    font-size: 16px;
}

.horizontal-box .card {
    flex: 0 0 calc(33.33% - 12rpx);
    margin: 0;
}

/* 基础容器样式 */
.promotion-detail-container {
  width: 100%;
  padding-bottom: env(safe-area-inset-bottom);
}

/* 库存状态 */
.stock-container {
  display: flex;
  align-items: center;
  gap: 12rpx;
  padding: 12rpx;
  margin-bottom: 24rpx;
  border-radius: 16rpx;
  background-color: #f0fdf4;
}

.stock-container.soldout {
  background-color: #fff5f5;
}

.stock-icon {
  font-size: 24rpx;
  color: #4cd964;
}

/* 规格选择 */
.specs-container {
  margin-bottom: 20rpx;
}

.specs-label {
  font-size: 24rpx;
  color: #2d3748;
  font-weight: 500;
  margin-bottom: 12rpx;
  display: block;
}

.specs-list {
  display: flex;
  flex-wrap: wrap;
  gap: 12rpx;
}

.spec-item {
  border: 2rpx solid #e2e8f0;
  border-radius: 12rpx;
  padding: 12rpx 20rpx;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-width: 120rpx;
  cursor: pointer;
  transition: all 0.3s ease;
}

.spec-item.active {
  border-color: #ff7eb3;
  background-color: rgba(255, 126, 179, 0.1);
}

.spec-item.disabled {
  border-color: #cbd5e0;
  background-color: #f9fafb;
  cursor: not-allowed;
  opacity: 0.7;
}

.spec-name {
  font-size: 22rpx;
  color: #2d3748;
  margin-bottom: 4rpx;
}

.spec-price {
  font-size: 20rpx;
  color: #ff5252;
  font-weight: 600;
}

.spec-stock {
  font-size: 18rpx;
  color: #a0aec0;
  margin-top: 4rpx;
}

/* 商品图片轮播 */
.product-swiper {
  position: relative;
  padding-top: 0; /* 适配导航栏高度 */
  /* height: 250rpx; */
}

.swiper-img {
  width: 100%;
  height: auto;
  /* height: 250rpx; */
  object-fit: cover;
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
  /* box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05); */
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
  /* box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05); */
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
  /* box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05); */
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
  /* box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05); */
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
  margin-top: 24rpx;
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
