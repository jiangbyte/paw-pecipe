<script lang="ts" setup>
import CustomNavbar from '@/components/CustomNavbar.vue'
import { onLoad } from '@dcloudio/uni-app'
import { computed, onUnmounted, ref } from 'vue'

// 状态管理
const orderInfo = ref<any>({
  ingredientId: '',
  name: '',
  spec: '',
  price: 0,
  count: 1,
  totalPrice: 0,
  cover: '', // 食材封面图（后续从接口获取）
})
const addressList = ref<any[]>([])
const selectedAddressId = ref('')
const selectedPaymentMethod = ref('wechat') // 默认微信支付
const loading = ref(true)
const error = ref('')
const isSubmitting = ref(false)

// 页面加载：获取订单参数和地址列表
onLoad((options) => {
  // 解析URL参数
  const { ingredientId, name, spec, price, count, totalPrice } = options
  if (!ingredientId || !name) {
    error.value = '订单参数错误'
    loading.value = false
    return
  }

  // 初始化订单信息
  orderInfo.value = {
    ingredientId,
    name: decodeURIComponent(name),
    spec: decodeURIComponent(spec || '默认规格'),
    price: Number(price) || 0,
    count: Number(count) || 1,
    totalPrice: Number(totalPrice) || 0,
    cover: getIngredientCover(ingredientId), // 根据食材ID获取封面图
  }

  // 获取收货地址列表
  fetchAddressList()
})

// 根据食材ID获取封面图（模拟接口返回）
function getIngredientCover(ingredientId: string) {
  const coverMap: Record<string, string> = {
    101: 'https://picsum.photos/seed/tomato/400/300',
    102: 'https://picsum.photos/seed/egg/400/300',
    103: 'https://picsum.photos/seed/scallion/400/300',
    104: 'https://picsum.photos/seed/salt/400/300',
    105: 'https://picsum.photos/seed/sugar/400/300',
    106: 'https://picsum.photos/seed/oil/400/300',
    107: 'https://picsum.photos/seed/chickenPowder/400/300',
    108: 'https://picsum.photos/seed/soySauce/400/300',
  }
  return coverMap[ingredientId] || 'https://picsum.photos/seed/ingredient/400/300'
}

// 模拟获取收货地址列表
async function fetchAddressList() {
  try {
    loading.value = true
    error.value = ''

    // 实际项目中替换为真实接口请求
    // const response = await axios.get('/api/user/addresses')
    // addressList.value = response.data.data

    // 模拟接口延迟
    await new Promise(resolve => setTimeout(resolve, 600))

    // 模拟地址数据
    addressList.value = [
      {
        id: 'addr1',
        receiver: '张三',
        phone: '138****1234',
        province: '广东省',
        city: '深圳市',
        district: '南山区',
        detail: '科技园路1号腾讯大厦10层',
        isDefault: true,
        postalCode: '518057',
      },
      {
        id: 'addr2',
        receiver: '张三',
        phone: '139****5678',
        province: '广东省',
        city: '广州市',
        district: '天河区',
        detail: '珠江新城冼村路5号',
        isDefault: false,
        postalCode: '510623',
      },
    ]

    // 默认选中默认地址
    const defaultAddress = addressList.value.find(addr => addr.isDefault)
    if (defaultAddress) {
      selectedAddressId.value = defaultAddress.id
    }
    else if (addressList.value.length > 0) {
      selectedAddressId.value = addressList.value[0].id
    }
  }
  catch (err) {
    error.value = '订单信息加载失败，请稍后重试'
    console.error('获取订单信息失败:', err)
  }
  finally {
    loading.value = false
  }
}

// 计算订单总金额（包含商品金额+运费，这里运费默认0）
const finalTotalPrice = computed(() => {
  const freight = 0 // 满减或包邮逻辑可在此扩展
  return (orderInfo.value.totalPrice + freight).toFixed(2)
})

// 选择收货地址
function selectAddress(addrId: string) {
  selectedAddressId.value = addrId
}

// 新增/编辑地址（跳转到地址管理页）
function goToAddressManage() {
  uni.navigateTo({
    url: '/pages/address/manage',
  })
}

// 选择支付方式
function selectPayment(method: string) {
  selectedPaymentMethod.value = method
}

// 提交订单
async function submitOrder() {
  // 验证地址是否选中
  if (!selectedAddressId.value) {
    uni.showToast({ title: '请选择收货地址', icon: 'none' })
    return
  }

  // 防止重复提交
  if (isSubmitting.value)
    return
  isSubmitting.value = true

  try {
    // 实际项目中调用提交订单接口
    console.log('提交订单:', {
      orderInfo: orderInfo.value,
      addressId: selectedAddressId.value,
      paymentMethod: selectedPaymentMethod.value,
      totalPrice: finalTotalPrice.value,
    })

    // 模拟接口延迟
    await new Promise(resolve => setTimeout(resolve, 1000))

    // 模拟订单创建成功（实际项目中获取真实订单号）
    const orderNo = `ORD${Date.now()}${Math.floor(Math.random() * 1000)}`

    // 跳转到支付页面
    uni.navigateTo({
      url: `/pages/pay/index?orderNo=${orderNo}&totalPrice=${finalTotalPrice.value}`,
    })
  }
  catch (err) {
    uni.showToast({ title: '订单提交失败，请稍后重试', icon: 'none' })
    console.error('提交订单失败:', err)
  }
  finally {
    isSubmitting.value = false
  }
}

// 生命周期：页面卸载时重置状态
onUnmounted(() => {
  isSubmitting.value = false
})

// // 获取当前选中的地址信息
// const selectedAddress = computed(() => {
//   return addressList.value.find(addr => addr.id === selectedAddressId.value) || null
// })
</script>

<template>
  <view class="order-confirm-container">
    <!-- 导航栏 -->
    <CustomNavbar title="确认订单" :show-back="true" />

    <!-- 加载中状态 -->
    <view v-if="loading" class="loading-state">
      <text class="loading-icon">
        ⏳
      </text>
      <text class="loading-text">
        加载订单信息中...
      </text>
    </view>

    <!-- 加载失败状态 -->
    <view v-if="error && !loading" class="error-state">
      <text class="error-icon">
        ❌
      </text>
      <text class="error-text">
        {{ error }}
      </text>
      <button class="retry-btn" @click="fetchAddressList()">
        重试
      </button>
    </view>

    <!-- 订单确认内容（加载成功后显示） -->
    <scroll-view v-if="!loading && !error" class="order-content" scroll-y>
      <!-- 收货地址区域 -->
      <view class="address-section">
        <view class="section-header">
          <text class="section-title">
            收货地址
          </text>
          <button class="edit-btn" @click="goToAddressManage()">
            <text class="edit-text">
              管理地址
            </text>
            <text class="edit-icon">
              →
            </text>
          </button>
        </view>

        <!-- 地址列表 -->
        <view class="address-list">
          <view
            v-for="address in addressList"
            :key="address.id"
            class="address-item"
            :class="{ active: selectedAddressId === address.id }"
            @click="selectAddress(address.id)"
          >
            <view class="address-header">
              <text class="receiver-name">
                {{ address.receiver }}
              </text>
              <text class="receiver-phone">
                {{ address.phone }}
              </text>
              <view v-if="address.isDefault" class="default-tag">
                默认地址
              </view>
            </view>
            <view class="address-detail">
              <text class="address-text">
                {{ address.province }}{{ address.city }}{{ address.district }}{{ address.detail }}
              </text>
            </view>
            <view class="select-icon" :class="{ selected: selectedAddressId === address.id }">
              ✅
            </view>
          </view>

          <!-- 无地址时显示 -->
          <view v-if="addressList.length === 0" class="no-address">
            <text class="no-address-icon">
              📮
            </text>
            <text class="no-address-text">
              暂无收货地址
            </text>
            <button class="add-address-btn" @click="goToAddressManage()">
              <text class="add-icon">
                +
              </text>
              <text class="add-text">
                添加新地址
              </text>
            </button>
          </view>
        </view>
      </view>

      <!-- 商品信息区域 -->
      <view class="goods-section">
        <view class="section-header">
          <text class="section-title">
            商品信息
          </text>
        </view>

        <view class="goods-card">
          <!-- 商品图片 -->
          <view class="goods-cover">
            <image :src="orderInfo.cover" mode="cover" class="goods-img" />
          </view>

          <!-- 商品信息 -->
          <view class="goods-info">
            <text class="goods-name">
              {{ orderInfo.name }}
            </text>
            <text class="goods-spec">
              规格：{{ orderInfo.spec }}
            </text>
            <view class="goods-price">
              <text class="price-text">
                ¥{{ orderInfo.price.toFixed(1) }}
              </text>
              <text class="count-text">
                ×{{ orderInfo.count }}
              </text>
            </view>
          </view>
        </view>
      </view>

      <!-- 支付方式区域 -->
      <view class="payment-section">
        <view class="section-header">
          <text class="section-title">
            支付方式
          </text>
        </view>

        <view class="payment-list">
          <view
            class="payment-item"
            :class="{ active: selectedPaymentMethod === 'wechat' }"
            @click="selectPayment('wechat')"
          >
            <view class="payment-icon wechat-icon">
              <text class="icon-text">
                💬
              </text>
            </view>
            <text class="payment-name">
              微信支付
            </text>
            <view class="payment-select" :class="{ selected: selectedPaymentMethod === 'wechat' }">
              ✅
            </view>
          </view>

          <view
            class="payment-item"
            :class="{ active: selectedPaymentMethod === 'alipay' }"
            @click="selectPayment('alipay')"
          >
            <view class="payment-icon alipay-icon">
              <text class="icon-text">
                💰
              </text>
            </view>
            <text class="payment-name">
              支付宝支付
            </text>
            <view class="payment-select" :class="{ selected: selectedPaymentMethod === 'alipay' }">
              ✅
            </view>
          </view>

          <view
            class="payment-item"
            :class="{ active: selectedPaymentMethod === 'balance' }"
            @click="selectPayment('balance')"
          >
            <view class="payment-icon balance-icon">
              <text class="icon-text">
                💳
              </text>
            </view>
            <text class="payment-name">
              余额支付
            </text>
            <view class="payment-select" :class="{ selected: selectedPaymentMethod === 'balance' }">
              ✅
            </view>
          </view>
        </view>
      </view>

      <!-- 订单摘要区域 -->
      <view class="order-summary">
        <view class="summary-item">
          <text class="summary-label">
            商品总价：
          </text>
          <text class="summary-value">
            ¥{{ orderInfo.totalPrice.toFixed(2) }}
          </text>
        </view>
        <view class="summary-item">
          <text class="summary-label">
            运费：
          </text>
          <text class="summary-value">
            ¥0.00
          </text>
        </view>
        <view class="summary-item">
          <text class="summary-label">
            优惠：
          </text>
          <text class="summary-value discount">
            ¥0.00
          </text>
        </view>
        <view class="summary-item total">
          <text class="summary-label">
            实付款：
          </text>
          <text class="summary-value total-price">
            ¥{{ finalTotalPrice }}
          </text>
        </view>
      </view>
    </scroll-view>

    <!-- 底部提交订单栏 -->
    <view v-if="!loading && !error" class="bottom-submit-bar">
      <view class="total-container">
        <text class="total-label">
          实付款：
        </text>
        <text class="total-price">
          ¥{{ finalTotalPrice }}
        </text>
      </view>
      <button
        class="submit-btn"
        :disabled="isSubmitting || !selectedAddressId.value"
        @click="submitOrder"
      >
        <text v-if="!isSubmitting" class="btn-text">
          提交订单
        </text>
        <text v-if="isSubmitting" class="btn-text">
          提交中...
        </text>
      </button>
    </view>
  </view>
</template>

<style scoped>
/* 基础容器 */
.order-confirm-container {
  width: 100vw;
  min-height: 100vh;
  background-color: #f9fafe;
  display: flex;
  flex-direction: column;
  padding-bottom: env(safe-area-inset-bottom);
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

/* 错误状态 */
.error-state {
  text-align: center;
  padding: 200rpx 0;
}

.error-icon {
  font-size: 80rpx;
  margin-bottom: 30rpx;
  display: block;
  color: #ff5252;
}

.error-text {
  font-size: 28rpx;
  color: #ff5252;
  margin-bottom: 30rpx;
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

/* 订单内容滚动区域 */
.order-content {
  flex: 1;
  padding: 20rpx;
}

/* 通用区域标题 */
.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16rpx;
}

.section-title {
  font-size: 28rpx;
  font-weight: 600;
  color: #2d3748;
}

/* 收货地址区域 */
.address-section {
  background-color: white;
  border-radius: 24rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
}

.edit-btn {
  background: transparent;
  color: #64b5f6;
  border: none;
  font-size: 22rpx;
  display: flex;
  align-items: center;
  gap: 6rpx;
  padding: 0;
}

.edit-text {
  font-weight: 500;
}

.edit-icon {
  font-size: 24rpx;
}

/* 地址列表 */
.address-list {
  margin-top: 16rpx;
}

.address-item {
  border: 2rpx solid #e2e8f0;
  border-radius: 16rpx;
  padding: 20rpx;
  margin-bottom: 16rpx;
  position: relative;
  cursor: pointer;
  transition: all 0.3s ease;
}

.address-item.active {
  border-color: #ff7eb3;
  background-color: rgba(255, 126, 179, 0.05);
}

.address-header {
  display: flex;
  align-items: center;
  margin-bottom: 12rpx;
}

.receiver-name {
  font-size: 26rpx;
  font-weight: 600;
  color: #2d3748;
  margin-right: 20rpx;
}

.receiver-phone {
  font-size: 24rpx;
  color: #4a5568;
}

.default-tag {
  margin-left: auto;
  background-color: #ff7eb3;
  color: white;
  font-size: 20rpx;
  padding: 4rpx 12rpx;
  border-radius: 12rpx;
}

.address-detail {
  margin-bottom: 8rpx;
}

.address-text {
  font-size: 24rpx;
  color: #4a5568;
  line-height: 1.5;
}

.select-icon {
  position: absolute;
  right: 20rpx;
  bottom: 20rpx;
  font-size: 28rpx;
  color: #e2e8f0;
}

.select-icon.selected {
  color: #ff7eb3;
  animation: selectPulse 0.5s ease;
}

/* 无地址状态 */
.no-address {
  text-align: center;
  padding: 40rpx 0;
  border: 2rpx dashed #e2e8f0;
  border-radius: 16rpx;
}

.no-address-icon {
  font-size: 60rpx;
  margin-bottom: 20rpx;
  display: block;
  color: #a0aec0;
}

.no-address-text {
  font-size: 24rpx;
  color: #a0aec0;
  margin-bottom: 24rpx;
  display: block;
}

.add-address-btn {
  background-color: #f5f6f7;
  color: #64b5f6;
  border: 2rpx dashed #64b5f6;
  border-radius: 32rpx;
  font-size: 24rpx;
  padding: 0 32rpx;
  height: 64rpx;
  line-height: 64rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12rpx;
}

.add-icon {
  font-size: 28rpx;
}

/* 商品信息区域 */
.goods-section {
  background-color: white;
  border-radius: 24rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
}

.goods-card {
  display: flex;
  gap: 20rpx;
  padding: 16rpx 0;
  border-top: 1rpx solid #f0f2f5;
  border-bottom: 1rpx solid #f0f2f5;
}

.goods-cover {
  width: 120rpx;
  height: 120rpx;
  border-radius: 16rpx;
  overflow: hidden;
}

.goods-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.goods-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.goods-name {
  font-size: 24rpx;
  font-weight: 500;
  color: #2d3748;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
  margin-bottom: 8rpx;
}

.goods-spec {
  font-size: 22rpx;
  color: #718096;
  margin-bottom: 12rpx;
}

.goods-price {
  display: flex;
  align-items: center;
  gap: 16rpx;
}

.price-text {
  font-size: 26rpx;
  font-weight: 600;
  color: #ff5252;
}

.count-text {
  font-size: 22rpx;
  color: #718096;
}

/* 支付方式区域 */
.payment-section {
  background-color: white;
  border-radius: 24rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
}

.payment-list {
  margin-top: 16rpx;
  display: flex;
  flex-direction: column;
  gap: 12rpx;
}

.payment-item {
  display: flex;
  align-items: center;
  gap: 20rpx;
  border: 2rpx solid #e2e8f0;
  border-radius: 16rpx;
  padding: 20rpx;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
}

.payment-item.active {
  border-color: #ff7eb3;
  background-color: rgba(255, 126, 179, 0.05);
}

.payment-icon {
  width: 60rpx;
  height: 60rpx;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.wechat-icon {
  background-color: #dcf1ff;
  color: #1aad19;
}

.alipay-icon {
  background-color: #e8f3ff;
  color: #00a1d6;
}

.balance-icon {
  background-color: #fff0e8;
  color: #ff7d00;
}

.icon-text {
  font-size: 32rpx;
}

.payment-name {
  font-size: 24rpx;
  color: #2d3748;
  flex: 1;
}

.payment-select {
  font-size: 28rpx;
  color: #e2e8f0;
}

.payment-select.selected {
  color: #ff7eb3;
  animation: selectPulse 0.5s ease;
}

/* 订单摘要区域 */
.order-summary {
  background-color: white;
  border-radius: 24rpx;
  padding: 24rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
}

.summary-item {
  display: flex;
  justify-content: space-between;
  padding: 12rpx 0;
  border-bottom: 1rpx solid #f0f2f5;
}

.summary-item:last-child {
  border-bottom: none;
}

.summary-label {
  font-size: 24rpx;
  color: #718096;
}

.summary-value {
  font-size: 24rpx;
  color: #2d3748;
}

.discount {
  color: #ff5252;
}

.summary-item.total {
  padding-top: 16rpx;
}

.total-price {
  font-size: 32rpx;
  font-weight: 700;
  color: #ff5252;
}

/* 底部提交订单栏 */
.bottom-submit-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: white;
  padding: 20rpx;
  border-top: 1rpx solid #f0f2f5;
  display: flex;
  align-items: center;
  justify-content: space-between;
  z-index: 999;
  padding-bottom: calc(env(safe-area-inset-bottom) + 20rpx);
}

.total-container {
  display: flex;
  align-items: baseline;
  gap: 8rpx;
}

.total-label {
  font-size: 26rpx;
  color: #2d3748;
  font-weight: 500;
}

.bottom-submit-bar .total-price {
  font-size: 36rpx;
  font-weight: 700;
  color: #ff5252;
}

.submit-btn {
  background: linear-gradient(135deg, #ff7eb3, #ff5252);
  color: white;
  border: none;
  border-radius: 32rpx;
  font-size: 26rpx;
  font-weight: 600;
  padding: 0 48rpx;
  height: 80rpx;
  line-height: 80rpx;
  animation: pulse 2s infinite;
}

.submit-btn:disabled {
  background-color: #cbd5e0;
  color: #718096;
  animation: none;
}

/* 动画效果 */
@keyframes selectPulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.3); }
  100% { transform: scale(1); }
}

@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.03); }
  100% { transform: scale(1); }
}

/* 响应式适配 */
@media (min-width: 750rpx) {
  .order-content {
    padding: 30rpx;
  }

  .address-section, .goods-section, .payment-section, .order-summary {
    padding: 30rpx;
    margin-bottom: 30rpx;
  }

  .bottom-submit-bar {
    padding: 24rpx 30rpx;
    padding-bottom: calc(env(safe-area-inset-bottom) + 24rpx);
  }

  .submit-btn {
    padding: 0 60rpx;
  }
}

/* 深色模式适配 */
@media (prefers-color-scheme: dark) {
  .order-confirm-container {
    background-color: #1a202c;
  }

  .address-section, .goods-section, .payment-section, .order-summary, .bottom-submit-bar {
    background-color: #2d3748;
  }

  .section-title, .receiver-name, .address-text, .goods-name, .payment-name, .summary-value, .total-label {
    color: #f7fafc;
  }

  .receiver-phone, .goods-spec, .count-text, .summary-label {
    color: #a0aec0;
  }

  .address-item, .payment-item {
    border-color: #4a5568;
  }

  .address-item.active, .payment-item.active {
    background-color: rgba(255, 126, 179, 0.2);
  }

  .no-address {
    border-color: #4a5568;
  }

  .add-address-btn {
    background-color: #384459;
    border-color: #64b5f6;
    color: #64b5f6;
  }

  .goods-card {
    border-top-color: #4a5568;
    border-bottom-color: #4a5568;
  }

  .summary-item {
    border-bottom-color: #4a5568;
  }
}
</style>
