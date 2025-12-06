<script lang="ts" setup>
import CustomNavbar from '@/components/CustomNavbar.vue'
import CustomTabBar from '@/components/CustomTabBar.vue'
import EmptyState from '@/components/EmptyState.vue'
import { computed, onMounted, ref, watch } from 'vue'

// 模拟购物车数据（结合后端实体字段）
interface CartItem {
  id: string
  userId: string
  productId: string
  skuId?: string
  flashId?: string
  quantity: number
  selected: boolean
  specId?: string
  // 前端补充字段
  productName: string
  productCover: string
  originalPrice: number
  price: number // 实际购买价（抢购价/原价）
  stock: number
  checked?: boolean // 前端选中状态（映射selected）
}

// 模拟商品信息（实际项目中从商品接口获取）
const productMap = {
  1: { name: '番茄买1送1', cover: 'https://picsum.photos/seed/tomato/400/300', originalPrice: 12.9, price: 6.9, stock: 324 },
  2: { name: '鸡蛋10枚特惠装', cover: 'https://picsum.photos/seed/egg/400/300', originalPrice: 19.9, price: 12.9, stock: 156 },
  3: { name: '优质小葱 bundle', cover: 'https://picsum.photos/seed/scallion/400/300', originalPrice: 8.9, price: 4.9, stock: 218 },
  4: { name: '精品土豆5斤装', cover: 'https://picsum.photos/seed/potato/400/300', originalPrice: 25.9, price: 15.9, stock: 89 },
}

// 购物车列表
const cartList = ref<CartItem[]>([
  {
    id: 'cart_1',
    userId: 'user_1001',
    productId: '1',
    skuId: 'sku_101',
    flashId: 'flash_1',
    quantity: 2,
    selected: true,
    specId: 'spec_1',
    productName: productMap['1'].name,
    productCover: productMap['1'].cover,
    originalPrice: productMap['1'].originalPrice,
    price: productMap['1'].price,
    stock: productMap['1'].stock,
    checked: true,
  },
  {
    id: 'cart_2',
    userId: 'user_1001',
    productId: '2',
    skuId: 'sku_102',
    flashId: 'flash_2',
    quantity: 1,
    selected: false,
    specId: 'spec_2',
    productName: productMap['2'].name,
    productCover: productMap['2'].cover,
    originalPrice: productMap['2'].originalPrice,
    price: productMap['2'].price,
    stock: productMap['2'].stock,
    checked: false,
  },
  {
    id: 'cart_3',
    userId: 'user_1001',
    productId: '3',
    skuId: 'sku_103',
    flashId: 'flash_3',
    quantity: 3,
    selected: true,
    specId: 'spec_3',
    productName: productMap['3'].name,
    productCover: productMap['3'].cover,
    originalPrice: productMap['3'].originalPrice,
    price: productMap['3'].price,
    stock: productMap['3'].stock,
    checked: true,
  },
])

// 全选状态
const allChecked = ref(false)

// 计算选中的商品
const selectedItems = computed(() => {
  return cartList.value.filter(item => item.checked)
})

// 计算总价
const totalPrice = computed(() => {
  return selectedItems.value.reduce((sum, item) => {
    return sum + item.price * item.quantity
  }, 0).toFixed(2)
})

// 计算选中商品数量
const selectedCount = computed(() => {
  return selectedItems.value.reduce((sum, item) => sum + item.quantity, 0)
})

// 监听购物车项选中状态变化，更新全选状态
watch(() => cartList.value.map(item => item.checked), () => {
  if (cartList.value.length === 0) {
    allChecked.value = false
    return
  }
  allChecked.value = cartList.value.every(item => item.checked)
}, { deep: true })

// 全选/取消全选
function toggleAllCheck() {
  allChecked.value = !allChecked.value
  cartList.value.forEach((item) => {
    item.checked = allChecked.value
    item.selected = allChecked.value // 同步到后端字段
  })
}

// 单个商品选中/取消
function toggleItemCheck(index: number) {
  cartList.value[index].checked = !cartList.value[index].checked
  cartList.value[index].selected = cartList.value[index].checked // 同步到后端字段
}

// 增加商品数量
function increaseQuantity(index: number) {
  const item = cartList.value[index]
  if (item.quantity >= item.stock) {
    uni.showToast({ title: '已达库存上限', icon: 'none' })
    return
  }
  item.quantity += 1
}

// 减少商品数量
function decreaseQuantity(index: number) {
  const item = cartList.value[index]
  if (item.quantity <= 1) {
    return
  }
  item.quantity -= 1
}

// 删除购物车项
function deleteCartItem(index: number) {
  uni.showModal({
    title: '提示',
    content: '确定要删除该商品吗？',
    success: (res) => {
      if (res.confirm) {
        cartList.value.splice(index, 1)
        uni.showToast({ title: '删除成功', icon: 'success' })
      }
    },
  })
}

// 结算
function handleCheckout() {
  if (selectedItems.value.length === 0) {
    uni.showToast({ title: '请选择要结算的商品', icon: 'none' })
    return
  }
  // 实际项目中跳转到结算页面，携带选中的购物车项ID
  uni.navigateTo({
    url: `/pages/checkout/index?cartIds=${selectedItems.value.map(item => item.id).join(',')}`,
  })
}

onMounted(() => {
  // 实际项目中这里调用购物车列表接口
  // uni.request({
  //   url: '/api/cart/list',
  //   success: (res) => {
  //     // 结合商品接口补充商品信息
  //     cartList.value = res.data.map(item => ({
  //       ...item,
  //       checked: item.selected,
  //       productName: productMap[item.productId]?.name || '',
  //       productCover: productMap[item.productId]?.cover || '',
  //       originalPrice: productMap[item.productId]?.originalPrice || 0,
  //       price: productMap[item.productId]?.price || 0,
  //       stock: productMap[item.productId]?.stock || 0
  //     }))
  //   }
  // })
})
</script>

<template>
  <view class="cart-container">
    <!-- 导航栏 -->
    <CustomNavbar title="我的购物车" />

    <!-- 购物车主内容 -->
    <view class="cart-content">
      <!-- 空状态 -->
      <EmptyState
        v-if="cartList.length === 0"
        icon="🛒"
        title="购物车空空如也～"
        description="快去挑选心仪的商品吧"
      />

      <!-- 购物车列表 -->
      <view v-else class="cart-list">
        <!-- 全选栏 -->
        <view class="cart-select-all">
          <checkbox
            :checked="allChecked"
            class="checkbox-all"
            @change="toggleAllCheck"
          />
          <text class="select-all-text">
            全选
          </text>
        </view>

        <!-- 购物车项 -->
        <view v-for="(item, index) in cartList" :key="item.id" class="cart-item">
          <!-- 选中框 -->
          <checkbox
            :checked="item.checked"
            class="checkbox-item"
            @change="toggleItemCheck(index)"
          />

          <!-- 商品图片 -->
          <image
            :src="item.productCover"
            class="product-cover"
            mode="aspectFill"
          />

          <!-- 商品信息 -->
          <view class="product-info">
            <view class="product-name">
              {{ item.productName }}
            </view>
            <view class="price-row">
              <text class="current-price">
                ¥{{ item.price }}
              </text>
              <text class="original-price">
                ¥{{ item.originalPrice }}
              </text>
            </view>
          </view>

          <!-- 数量控制 -->
          <view class="quantity-control">
            <button
              class="btn-minus"
              :disabled="item.quantity <= 1"
              @click="decreaseQuantity(index)"
            >
              -
            </button>
            <input
              v-model="item.quantity"
              class="quantity-input"
              type="number"
              min="1"
              :max="item.stock"
            >
            <button
              class="btn-plus"
              :disabled="item.quantity >= item.stock"
              @click="increaseQuantity(index)"
            >
              +
            </button>
          </view>

          <!-- 删除按钮 -->
          <button
            class="delete-btn"
            @click="deleteCartItem(index)"
          >
            🗑️
          </button>
        </view>
      </view>
    </view>

    <!-- 底部结算栏 -->
    <view v-if="cartList.length > 0" class="cart-footer">
      <view class="total-info">
        <text class="total-label">
          合计：
        </text>
        <text class="total-price">
          ¥{{ totalPrice }}
        </text>
        <text class="count">
          ({{ selectedCount }}件商品)
        </text>
      </view>
      <button class="checkout-btn" @click="handleCheckout">
        结算
      </button>
    </view>

    <!-- 底部标签栏 -->
    <CustomTabBar />
  </view>
</template>

<style scoped>
.cart-container {
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  padding-bottom: env(safe-area-inset-bottom);
  background-color: #f5f5f5;
}

.cart-content {
  flex: 1;
  overflow-y: auto;
}

.cart-list {
  background-color: #fff;
  margin-bottom: 20rpx;
}

.cart-select-all {
  display: flex;
  align-items: center;
  padding: 20rpx;
  border-bottom: 1rpx solid #eee;
}

.checkbox-all {
  transform: scale(1.2);
  margin-right: 10rpx;
}

.select-all-text {
  font-size: 32rpx;
  color: #333;
}

.cart-item {
  display: flex;
  align-items: center;
  padding: 20rpx;
  border-bottom: 1rpx solid #eee;
}

.checkbox-item {
  transform: scale(1.2);
  margin-right: 20rpx;
}

.product-cover {
  width: 120rpx;
  height: 120rpx;
  border-radius: 8rpx;
  margin-right: 20rpx;
}

.product-info {
  flex: 1;
}

.product-name {
  font-size: 28rpx;
  color: #333;
  margin-bottom: 10rpx;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.price-row {
  display: flex;
  align-items: baseline;
}

.current-price {
  font-size: 30rpx;
  color: #ff4757;
  font-weight: 600;
  margin-right: 10rpx;
}

.original-price {
  font-size: 24rpx;
  color: #999;
  text-decoration: line-through;
}

.quantity-control {
  display: flex;
  align-items: center;
  margin-right: 20rpx;
}

.btn-minus, .btn-plus {
  width: 40rpx;
  height: 40rpx;
  line-height: 40rpx;
  text-align: center;
  border: 1rpx solid #eee;
  background-color: #f5f5f5;
  border-radius: 4rpx;
  font-size: 28rpx;
}

.quantity-input {
  width: 60rpx;
  height: 40rpx;
  text-align: center;
  border: 1rpx solid #eee;
  margin: 0 10rpx;
  font-size: 24rpx;
}

.delete-btn {
  width: 40rpx;
  height: 40rpx;
  line-height: 40rpx;
  text-align: center;
  border: none;
  background: transparent;
  font-size: 24rpx;
  color: #999;
}

.delete-btn:active {
  color: #ff4757;
}

.cart-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20rpx;
  background-color: #fff;
  border-top: 1rpx solid #eee;
  position: sticky;
  bottom: 90rpx;
  z-index: 99;
}

.total-info {
  display: flex;
  align-items: baseline;
}

.total-label {
  font-size: 28rpx;
  color: #333;
}

.total-price {
  font-size: 32rpx;
  color: #ff4757;
  font-weight: 600;
  margin: 0 10rpx;
}

.count {
  font-size: 24rpx;
  color: #999;
}

.checkout-btn {
  width: 200rpx;
  height: 80rpx;
  line-height: 80rpx;
  text-align: center;
  background-color: #ff4757;
  color: #fff;
  border-radius: 40rpx;
  font-size: 30rpx;
  border: none;
}

.checkout-btn:active {
  background-color: #ff3040;
}
</style>
