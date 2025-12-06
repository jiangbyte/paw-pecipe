<script lang="ts" setup>
import CustomNavbar from '@/components/CustomNavbar.vue'
import { onLoad } from '@dcloudio/uni-app'
import { computed, ref } from 'vue'

// 状态管理
const ingredientDetail = ref<any>({})
const loading = ref(true)
const error = ref('')
const buyCount = ref(1)
const selectedSpec = ref('')
const isSoldOut = ref(false)

// 页面加载：获取食材ID和名称
onLoad((options) => {
  const { id, name } = options
  if (!id) {
    error.value = '食材ID不存在'
    loading.value = false
    return
  }

  // 请求食材详情数据
  fetchIngredientDetail(id, name)
})

// 模拟API请求：根据食材ID获取详情
async function fetchIngredientDetail(id: string, name: string) {
  try {
    loading.value = true
    error.value = ''

    // 实际项目中替换为真实接口请求
    // const response = await axios.get(`/api/ingredients/${id}`)
    // ingredientDetail.value = response.data.data

    // 模拟接口延迟
    await new Promise(resolve => setTimeout(resolve, 800))

    // 模拟食材详情数据（根据不同ID返回不同数据）
    ingredientDetail.value = getMockIngredientDetail(id, name)

    // 初始化选中规格（默认选中第一个）
    if (ingredientDetail.value.specs && ingredientDetail.value.specs.length > 0) {
      selectedSpec.value = ingredientDetail.value.specs[0].id
    }

    // 检查是否售罄
    isSoldOut.value = ingredientDetail.value.stock <= 0
  }
  catch (err) {
    error.value = '食材详情加载失败，请稍后重试'
    console.error('获取食材详情失败:', err)
  }
  finally {
    loading.value = false
  }
}

// 模拟不同食材的详情数据
function getMockIngredientDetail(id: string, name: string) {
  // 食材ID映射（与推荐组件中的ID对应）
  const ingredientMap: Record<string, any> = {
    101: { // 新鲜番茄
      id: 101,
      name: decodeURIComponent(name) || '新鲜番茄',
      cover: 'https://picsum.photos/seed/tomato/800/600',
      images: [
        'https://picsum.photos/seed/tomato1/800/600',
        'https://picsum.photos/seed/tomato2/800/600',
        'https://picsum.photos/seed/tomato3/800/600',
      ],
      originalPrice: 8.9,
      discountPrice: 6.9,
      discount: '7.7折',
      sales: 2356,
      stock: 128,
      tag: '必备',
      brand: '新鲜果蔬',
      origin: '山东寿光',
      shelfLife: '7天',
      storageMethod: '冷藏保存',
      delivery: '全国顺丰包邮',
      description: '精选山东寿光新鲜番茄，自然成熟，酸甜多汁，无催熟剂，富含维生素C。适合炒菜、凉拌、做汤等多种烹饪方式，是家庭日常烹饪必备食材。',
      specs: [
        { id: 'spec1', name: '500g/份', price: 6.9, stock: 86 },
        { id: 'spec2', name: '1kg/份', price: 12.5, stock: 42 },
        { id: 'spec3', name: '2kg/份', price: 23.9, stock: 35 },
      ],
      details: [
        '产地直供：山东寿光蔬菜基地直采',
        '品质保证：自然成熟，无农药残留',
        '新鲜直达：采摘后24小时内发货',
        '包装方式：透气保鲜盒+防震泡沫',
        '售后保障：坏果包赔，不满意可退换',
      ],
      service: ['坏果包赔', '次日达', '7天无理由退换', '专业保鲜包装'],
    },
    102: { // 土鸡蛋
      id: 102,
      name: decodeURIComponent(name) || '土鸡蛋',
      cover: 'https://picsum.photos/seed/egg/800/600',
      images: [
        'https://picsum.photos/seed/egg1/800/600',
        'https://picsum.photos/seed/egg2/800/600',
      ],
      originalPrice: 15.9,
      discountPrice: 12.9,
      discount: '8.1折',
      sales: 1892,
      stock: 96,
      tag: '优选',
      brand: '农家生态',
      origin: '安徽黄山',
      shelfLife: '30天',
      storageMethod: '常温避光',
      delivery: '京东冷链配送',
      description: '农家散养土鸡蛋，天然谷物喂养，无激素添加。蛋黄饱满，蛋清浓稠，营养丰富，口感香醇。适合煎、炒、煮、蒸等多种烹饪方式，是家庭营养早餐的优质选择。',
      specs: [
        { id: 'spec1', name: '10枚/盒', price: 12.9, stock: 56 },
        { id: 'spec2', name: '20枚/盒', price: 24.5, stock: 32 },
        { id: 'spec3', name: '30枚/盒', price: 35.9, stock: 8 },
      ],
      details: [
        '农家散养：自然觅食，谷物补充',
        '营养丰富：蛋白质含量高于普通鸡蛋',
        '无添加：不含激素、抗生素',
        '新鲜保证：48小时内产蛋发货',
        '包装安全：防震蛋托+泡沫箱',
      ],
      service: ['破损包赔', '冷链配送', '新鲜保障', '售后无忧'],
    },
    // 可扩展其他食材详情...
    default: {
      id: Number.parseInt(id),
      name: decodeURIComponent(name) || '新鲜食材',
      cover: 'https://picsum.photos/seed/ingredient/800/600',
      images: [
        'https://picsum.photos/seed/ingredient1/800/600',
      ],
      originalPrice: 9.9,
      discountPrice: 7.9,
      discount: '8折',
      sales: 1234,
      stock: 67,
      tag: '推荐',
      brand: '优选食材',
      origin: '产地直供',
      shelfLife: '7天',
      storageMethod: '冷藏保存',
      delivery: '全国包邮',
      description: '精选新鲜食材，品质保证，新鲜直达。适合多种烹饪方式，是家庭日常烹饪的优质选择。',
      specs: [
        { id: 'spec1', name: '标准装', price: 7.9, stock: 45 },
        { id: 'spec2', name: '家庭装', price: 14.9, stock: 22 },
      ],
      details: [
        '品质保证：严格筛选，新鲜直达',
        '产地直供：减少中间环节',
        '包装保鲜：专业保鲜包装',
        '售后保障：不满意可退换',
      ],
      service: ['品质保证', '全国包邮', '售后无忧'],
    },
  }

  return ingredientMap[id] || ingredientMap.default
}

// 计算当前选中规格的价格
const currentPrice = computed(() => {
  if (!selectedSpec.value || !ingredientDetail.value.specs)
    return ingredientDetail.value.discountPrice
  const spec = ingredientDetail.value.specs.find((s: any) => s.id === selectedSpec.value)
  return spec ? spec.price : ingredientDetail.value.discountPrice
})

// 计算当前选中规格的库存
const currentStock = computed(() => {
  if (!selectedSpec.value || !ingredientDetail.value.specs)
    return ingredientDetail.value.stock
  const spec = ingredientDetail.value.specs.find((s: any) => s.id === selectedSpec.value)
  return spec ? spec.stock : ingredientDetail.value.stock
})

// 切换规格
function selectSpec(specId: string) {
  selectedSpec.value = specId
  // 切换规格后重置购买数量为1
  buyCount.value = 1
}

// 增减购买数量
function changeCount(type: 'add' | 'reduce') {
  if (type === 'add') {
    if (buyCount.value < currentStock.value) {
      buyCount.value++
    }
    else {
      uni.showToast({ title: `库存不足，最多可购买${currentStock.value}件`, icon: 'none' })
    }
  }
  else {
    if (buyCount.value > 1) {
      buyCount.value--
    }
  }
}

// 直接输入购买数量
function inputCount(value: string) {
  const num = Number.parseInt(value)
  if (Number.isNaN(num) || num < 1) {
    buyCount.value = 1
  }
  else {
    buyCount.value = Math.min(num, currentStock.value)
  }
}

// 加入购物车
function addToCart() {
  if (isSoldOut.value || currentStock.value <= 0) {
    uni.showToast({ title: '商品已售罄', icon: 'none' })
    return
  }

  // 获取当前选中规格信息
  const specInfo = ingredientDetail.value.specs?.find((s: any) => s.id === selectedSpec.value) || {
    id: 'default',
    name: '默认规格',
  }

  uni.showToast({ title: '已加入购物车', icon: 'success' })

  // 实际项目中调用购物车接口
  console.log('加入购物车:', {
    ingredientId: ingredientDetail.value.id,
    name: ingredientDetail.value.name,
    spec: specInfo.name,
    price: currentPrice.value,
    count: buyCount.value,
    totalPrice: currentPrice.value * buyCount.value,
  })

  // 模拟更新购物车数量（实际项目中从接口获取）
  let cartCount = uni.getStorageSync('cartCount') || 0
  cartCount += buyCount.value
  uni.setStorageSync('cartCount', cartCount)
}

// 立即购买
function buyNow() {
  if (isSoldOut.value || currentStock.value <= 0) {
    uni.showToast({ title: '商品已售罄', icon: 'none' })
    return
  }

  // 获取当前选中规格信息
  const specInfo = ingredientDetail.value.specs?.find((s: any) => s.id === selectedSpec.value) || {
    id: 'default',
    name: '默认规格',
  }

  // 跳转到下单页面
  uni.navigateTo({
    url: `/pages/order/confirm?ingredientId=${ingredientDetail.value.id}&name=${encodeURIComponent(ingredientDetail.value.name)}&spec=${encodeURIComponent(specInfo.name)}&price=${currentPrice.value}&count=${buyCount.value}&totalPrice=${currentPrice.value * buyCount.value}`,
  })
}

// 获取标签样式
function getTagClass() {
  const tagMap: Record<string, string> = {
    必备: 'tag-essential',
    优选: 'tag-premium',
    调味: 'tag-seasoning',
    主料: 'tag-main',
    基础: 'tag-basic',
    提鲜: 'tag-fresh',
    增香: 'tag-flavor',
    推荐: 'tag-recommend',
  }
  return tagMap[ingredientDetail.value.tag] || 'tag-default'
}
</script>

<template>
  <view class="ingredient-detail-container">
    <!-- 导航栏 -->
    <CustomNavbar :title="ingredientDetail.name || '食材详情'" :show-back="true" />

    <!-- 加载中状态 -->
    <view v-if="loading" class="loading-state">
      <text class="loading-icon">
        ⏳
      </text>
      <text class="loading-text">
        加载中...
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
      <button class="retry-btn" @click="fetchIngredientDetail(ingredientDetail.id, ingredientDetail.name)">
        重试
      </button>
    </view>

    <!-- 食材详情内容（加载成功后显示） -->
    <view v-if="!loading && !error" class="detail-content">
      <!-- 食材图片轮播 -->
      <view class="ingredient-swiper">
        <swiper
          indicator-dots
          indicator-color="rgba(255,255,255,0.5)"
          indicator-active-color="#ff7eb3"
          circular
          autoplay
          interval="3000"
        >
          <swiper-item v-for="(img, index) in ingredientDetail.images" :key="index">
            <image :src="img" mode="widthFix" class="swiper-img" />
          </swiper-item>
        </swiper>

        <!-- 标签区域 -->
        <view class="ingredient-tags">
          <view class="tag" :class="getTagClass()">
            <text class="tag-text">
              {{ ingredientDetail.tag }}
            </text>
          </view>
          <view v-if="ingredientDetail.discount" class="discount-tag">
            <text class="discount-text">
              {{ ingredientDetail.discount }}
            </text>
          </view>
        </view>
      </view>

      <!-- 食材基本信息 -->
      <view class="ingredient-base-info">
        <text class="ingredient-name">
          {{ ingredientDetail.name }}
        </text>

        <!-- 价格信息 -->
        <view class="price-container">
          <text class="discount-price">
            ¥{{ currentPrice.toFixed(1) }}
          </text>
          <text class="original-price">
            ¥{{ ingredientDetail.originalPrice.toFixed(1) }}
          </text>
          <text class="sales-text">
            已售 {{ ingredientDetail.sales }}+
          </text>
        </view>

        <!-- 库存状态 -->
        <view class="stock-container" :class="{ soldout: isSoldOut || currentStock <= 0 }">
          <text class="stock-icon">
            📦
          </text>
          <text class="stock-text">
            {{ isSoldOut || currentStock <= 0 ? '已售罄' : `库存剩余 ${currentStock} 件` }}
          </text>
        </view>

        <!-- 规格选择 -->
        <view v-if="ingredientDetail.specs && ingredientDetail.specs.length > 0" class="specs-container">
          <text class="specs-label">
            选择规格：
          </text>
          <view class="specs-list">
            <view
              v-for="spec in ingredientDetail.specs"
              :key="spec.id"
              class="spec-item"
              :class="{ active: selectedSpec === spec.id, disabled: spec.stock <= 0 }"
              @click="selectSpec(spec.id)"
            >
              <text class="spec-name">
                {{ spec.name }}
              </text>
              <text class="spec-price">
                ¥{{ spec.price.toFixed(1) }}
              </text>
              <text v-if="spec.stock <= 0" class="spec-stock">
                已售罄
              </text>
            </view>
          </view>
        </view>

        <!-- 购买数量选择 -->
        <view v-if="!(isSoldOut || currentStock <= 0)" class="count-selector">
          <text class="count-label">
            购买数量：
          </text>
          <view class="count-controls">
            <button class="control-btn" :disabled="buyCount <= 1" @click="changeCount('reduce')">
              <text class="control-icon">
                -
              </text>
            </button>
            <input
              v-model.number="buyCount"
              type="number"
              class="count-input"
              @input="inputCount($event.target.value)"
            >
            <button class="control-btn" :disabled="buyCount >= currentStock" @click="changeCount('add')">
              <text class="control-icon">
                +
              </text>
            </button>
          </view>
        </view>
      </view>

      <!-- 食材详情内容 -->
      <scroll-view class="detail-scroll" scroll-y>
        <!-- 食材描述 -->
        <view class="ingredient-description">
          <text class="section-title">
            食材介绍
          </text>
          <text class="desc-text">
            {{ ingredientDetail.description }}
          </text>
        </view>

        <!-- 基本信息 -->
        <view class="basic-info">
          <text class="section-title">
            基本信息
          </text>
          <view class="info-list">
            <view class="info-item">
              <text class="info-label">
                品牌：
              </text>
              <text class="info-value">
                {{ ingredientDetail.brand }}
              </text>
            </view>
            <view class="info-item">
              <text class="info-label">
                产地：
              </text>
              <text class="info-value">
                {{ ingredientDetail.origin }}
              </text>
            </view>
            <view class="info-item">
              <text class="info-label">
                保质期：
              </text>
              <text class="info-value">
                {{ ingredientDetail.shelfLife }}
              </text>
            </view>
            <view class="info-item">
              <text class="info-label">
                储存方式：
              </text>
              <text class="info-value">
                {{ ingredientDetail.storageMethod }}
              </text>
            </view>
            <view class="info-item">
              <text class="info-label">
                配送方式：
              </text>
              <text class="info-value">
                {{ ingredientDetail.delivery }}
              </text>
            </view>
          </view>
        </view>

        <!-- 食材特点 -->
        <view class="ingredient-features">
          <text class="section-title">
            食材特点
          </text>
          <view class="features-list">
            <view v-for="(feature, index) in ingredientDetail.details" :key="index" class="feature-item">
              <text class="feature-icon">
                ✅
              </text>
              <text class="feature-text">
                {{ feature }}
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
            <view v-for="(service, index) in ingredientDetail.service" :key="index" class="service-item">
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
    </view>

    <!-- 底部操作栏 -->
    <view v-if="!loading && !error" class="bottom-action-bar">
      <button
        class="cart-btn"
        :disabled="isSoldOut || currentStock <= 0"
        @click="addToCart"
      >
        <text class="cart-icon">
          🛒
        </text>
        <text class="btn-text">
          加入购物车
        </text>
      </button>
      <button
        class="buy-now-btn"
        :disabled="isSoldOut || currentStock <= 0"
        @click="buyNow"
      >
        <text class="btn-text">
          立即购买
        </text>
        <text class="total-price">
          ¥{{ (currentPrice * buyCount).toFixed(1) }}
        </text>
      </button>
    </view>
  </view>
</template>

<style scoped>
/* 基础容器 */
.ingredient-detail-container {
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

/* 详情内容容器 */
.detail-content {
  flex: 1;
  display: flex;
  flex-direction: column;
}

/* 食材图片轮播 */
.ingredient-swiper {
  position: relative;
  width: 100%;
}

.swiper-img {
  width: 100%;
  height: auto;
}

/* 标签区域 */
.ingredient-tags {
  position: absolute;
  top: 20rpx;
  left: 20rpx;
  display: flex;
  gap: 12rpx;
}

.tag {
  padding: 8rpx 16rpx;
  border-radius: 16rpx;
  font-size: 22rpx;
  font-weight: 600;
  color: white;
}

/* 标签样式 */
.tag-essential { background-color: #ff7eb3; }
.tag-premium { background-color: #64b5f6; }
.tag-seasoning { background-color: #ff9f43; }
.tag-main { background-color: #4cd964; }
.tag-basic { background-color: #9c27b0; }
.tag-fresh { background-color: #00bcd4; }
.tag-flavor { background-color: #ff5252; }
.tag-recommend { background-color: #4caf50; }
.tag-default { background-color: #a0aec0; }

.discount-tag {
  position: absolute;
  top: 20rpx;
  right: 20rpx;
  background: linear-gradient(135deg, #ff7eb3, #ff5252);
  color: white;
  font-size: 24rpx;
  font-weight: 700;
  padding: 10rpx 20rpx;
  border-radius: 20rpx;
}

/* 食材基本信息 */
.ingredient-base-info {
  padding: 20rpx;
  background-color: white;
  border-bottom: 1rpx solid #f0f2f5;
}

.ingredient-name {
  font-size: 32rpx;
  font-weight: 600;
  color: #2d3748;
  margin-bottom: 16rpx;
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

.sales-text {
  font-size: 22rpx;
  color: #718096;
  margin-left: auto;
}

/* 库存状态 */
.stock-container {
  display: flex;
  align-items: center;
  gap: 12rpx;
  margin-bottom: 20rpx;
  padding: 12rpx;
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

.stock-container.soldout .stock-icon {
  color: #ff5252;
}

.stock-text {
  font-size: 24rpx;
  color: #0f766e;
  flex: 1;
}

.stock-container.soldout .stock-text {
  color: #b91c1c;
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

/* 购买数量选择 */
.count-selector {
  display: flex;
  align-items: center;
  gap: 16rpx;
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

/* 详情滚动区域 */
.detail-scroll {
  flex: 1;
  padding: 20rpx;
}

/* 食材介绍 */
.ingredient-description {
  background-color: white;
  border-radius: 24rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
}

/* 基本信息 */
.basic-info {
  background-color: white;
  border-radius: 24rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
}

.info-list {
  display: flex;
  flex-direction: column;
  gap: 16rpx;
}

.info-item {
  display: flex;
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

/* 食材特点 */
.ingredient-features {
  background-color: white;
  border-radius: 24rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
}

.features-list {
  display: flex;
  flex-direction: column;
  gap: 16rpx;
}

.feature-item {
  display: flex;
  align-items: flex-start;
  gap: 12rpx;
}

.feature-icon {
  font-size: 24rpx;
  color: #4cd964;
  margin-top: 4rpx;
}

.feature-text {
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

/* 底部操作栏 */
.bottom-action-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: white;
  padding: 16rpx 20rpx;
  border-top: 1rpx solid #f0f2f5;
  display: flex;
  gap: 16rpx;
  z-index: 999;
  padding-bottom: calc(env(safe-area-inset-bottom) + 16rpx);
}

.cart-btn {
  flex: 1;
  background-color: #f5f6f7;
  color: #4a5568;
  border: none;
  border-radius: 32rpx;
  font-size: 26rpx;
  font-weight: 500;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12rpx;
  height: 80rpx;
}

.cart-icon {
  font-size: 32rpx;
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

.buy-now-btn:disabled, .cart-btn:disabled {
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

/* 响应式适配 */
@media (min-width: 750rpx) {
  .ingredient-base-info, .detail-scroll {
    padding: 30rpx;
  }

  .ingredient-description, .basic-info, .ingredient-features, .service-guarantee {
    padding: 30rpx;
    margin-bottom: 30rpx;
  }

  .bottom-action-bar {
    padding: 20rpx 30rpx;
    padding-bottom: calc(env(safe-area-inset-bottom) + 20rpx);
  }

  .count-input {
    width: 100rpx;
  }

  .buy-now-btn {
    flex: 2;
  }
}

/* 深色模式适配 */
@media (prefers-color-scheme: dark) {
  .ingredient-detail-container {
    background-color: #1a202c;
  }

  .ingredient-base-info, .ingredient-description, .basic-info, .ingredient-features, .service-guarantee, .bottom-action-bar {
    background-color: #2d3748;
  }

  .ingredient-name, .info-value, .feature-text, .service-text, .spec-name, .count-label, .btn-text, .total-price {
    color: #f7fafc;
  }

  .original-price, .sales-text, .stock-text, .info-label, .section-title, .spec-price, .spec-stock {
    color: #a0aec0;
  }

  .spec-item {
    border-color: #4a5568;
  }

  .spec-item.active {
    background-color: rgba(255, 126, 179, 0.2);
  }

  .spec-item.disabled {
    border-color: #4a5568;
    background-color: #384459;
  }

  .count-controls, .count-input {
    border-color: #4a5568;
    background-color: #2d3748;
  }

  .control-btn {
    background-color: #384459;
    color: #a0aec0;
  }

  .cart-btn {
    background-color: #384459;
    color: #a0aec0;
  }

  .stock-container {
    background-color: #0f766e;
  }

  .stock-container.soldout {
    background-color: #b91c1c;
  }
}
</style>
