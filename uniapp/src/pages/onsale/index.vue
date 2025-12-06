<script lang="ts" setup>
import CategoryTabs from '@/components/CategoryTabs.vue'
import CustomNavbar from '@/components/CustomNavbar.vue'
import CustomTabBar from '@/components/CustomTabBar.vue'
import EmptyState from '@/components/EmptyState.vue'
import PromotionCard from '@/components/PromotionCard.vue'
import { computed, onMounted, ref } from 'vue'

// 优惠活动数据
const promotionList = ref([
  {
    id: 1,
    title: '番茄买1送1',
    cover: 'https://picsum.photos/seed/promotion1/400/300',
    originalPrice: 12.9,
    discountPrice: 6.9,
    discount: '5折',
    sales: 1286,
    stock: 324,
    startTime: Date.now(),
    endTime: Date.now() + 3600 * 1000 * 8, // 8小时后结束
    tag: '限时特惠',
  },
  {
    id: 2,
    title: '鸡蛋10枚特惠装',
    cover: 'https://picsum.photos/seed/promotion2/400/300',
    originalPrice: 19.9,
    discountPrice: 12.9,
    discount: '6.5折',
    sales: 892,
    stock: 156,
    startTime: Date.now(),
    endTime: Date.now() + 3600 * 1000 * 3, // 3小时后结束
    tag: '热销推荐',
  },
  {
    id: 3,
    title: '优质小葱 bundle',
    cover: 'https://picsum.photos/seed/promotion3/400/300',
    originalPrice: 8.9,
    discountPrice: 4.9,
    discount: '5.5折',
    sales: 654,
    stock: 218,
    startTime: Date.now(),
    endTime: Date.now() + 3600 * 1000 * 12, // 12小时后结束
    tag: '新人专享',
  },
  {
    id: 4,
    title: '精品土豆5斤装',
    cover: 'https://picsum.photos/seed/promotion4/400/300',
    originalPrice: 25.9,
    discountPrice: 15.9,
    discount: '6.1折',
    sales: 987,
    stock: 89,
    startTime: Date.now(),
    endTime: Date.now() + 3600 * 1000 * 5, // 5小时后结束
    tag: '限时特惠',
  },
  {
    id: 5,
    title: '新鲜芒果2个装',
    cover: 'https://picsum.photos/seed/promotion5/400/300',
    originalPrice: 29.9,
    discountPrice: 19.9,
    discount: '6.7折',
    sales: 753,
    stock: 124,
    startTime: Date.now(),
    endTime: Date.now() + 3600 * 1000 * 7, // 7小时后结束
    tag: '爆款推荐',
  },
  {
    id: 6,
    title: '特级面粉2kg',
    cover: 'https://picsum.photos/seed/promotion6/400/300',
    originalPrice: 39.9,
    discountPrice: 25.9,
    discount: '6.5折',
    sales: 532,
    stock: 67,
    startTime: Date.now(),
    endTime: Date.now() + 3600 * 1000 * 15, // 15小时后结束
    tag: '限时特惠',
  },
])

// 倒计时相关
const countdowns = ref<Record<number, { hours: number, minutes: number, seconds: number }>>({})
const timer = ref<NodeJS.Timeout | null>(null)

// 计算倒计时
function calculateCountdown(endTime: number) {
  const now = Date.now()
  const diff = endTime - now

  if (diff <= 0) {
    return { hours: 0, minutes: 0, seconds: 0 }
  }

  const hours = Math.floor(diff / 3600000)
  const minutes = Math.floor((diff % 3600000) / 60000)
  const seconds = Math.floor((diff % 60000) / 1000)

  return {
    hours: hours < 10 ? hours : hours,
    minutes: minutes < 10 ? minutes : minutes,
    seconds: seconds < 10 ? seconds : seconds,
  }
}

// 初始化倒计时
onMounted(() => {
  // 初始计算
  promotionList.value.forEach((promotion) => {
    countdowns.value[promotion.id] = calculateCountdown(promotion.endTime)
  })

  // 每秒更新一次
  timer.value = setInterval(() => {
    promotionList.value.forEach((promotion) => {
      countdowns.value[promotion.id] = calculateCountdown(promotion.endTime)
    })
  }, 1000)

  return () => {
    if (timer.value)
      clearInterval(timer.value)
  }
})

// 分类标签
const tabList = [
  { value: '0', label: '全部' },
  { value: '1', label: '蔬菜' },
  { value: '2', label: '水果' },
  { value: '3', label: '粮油' },
  { value: '4', label: '肉类' },
  { value: '5', label: '蛋奶' },
]

const activeTab = ref('0')

// 筛选后的优惠列表（简化版，实际项目中根据标签筛选）
const filteredPromotions = computed(() => {
  if (activeTab.value === '0')
    return promotionList.value

  // 简单的分类映射（实际项目中应该在数据中添加category字段）
  const categoryMap: Record<string, string[]> = {
    1: ['番茄', '小葱', '土豆'],
    2: ['芒果'],
    3: ['面粉'],
    4: ['鸡蛋'],
    5: [],
  }

  return promotionList.value.filter((promotion) => {
    return categoryMap[activeTab.value!]?.some(keyword =>
      promotion.title.includes(keyword),
    )
  })
})

// 抢购按钮点击事件
function handleBuy(id: number) {
  // const promotion = promotionList.value.find(item => item.id === id)
  // if (promotion?.stock <= 0) {
  //   uni.showToast({ title: '已售罄', icon: 'none' })
  //   return
  // }

  // uni.showToast({ title: '抢购成功！', icon: 'success' })
  // // 实际项目中这里跳转到下单页面
  uni.navigateTo({ url: `/pages/onsale/detail?id=${id}` })
}
</script>

<template>
  <view class="promotion-container">
    <!-- 导航栏 -->
    <CustomNavbar title="优惠抢购" />

    <view class="sticky-header">
      <!-- 分类Tabs -->
      <CategoryTabs
        v-model="activeTab"
        :tab-list="tabList"
      />
    </view>

    <t-pull-down-refresh
      :loading-texts="['下拉刷新', '松手刷新', '正在刷新', '刷新完成']"
      :using-custom-navbar="true"
      :success-duration="600"
    >
      <!-- 空状态 -->
      <EmptyState
        v-if="filteredPromotions.length === 0"
        icon="🎁"
        title="暂无该分类的优惠哦～"
        description="换个分类试试吧"
      />

      <!-- 优惠卡片列表 -->
      <view class="promotion-list">
        <PromotionCard
          v-for="promotion in filteredPromotions"
          :key="promotion.id"
          v-bind="promotion"
          :countdown="countdowns[promotion.id]"
          @click="handleBuy"
        />
      </view>

      <view style="height: 150rpx;" />
    </t-pull-down-refresh>

    <!-- 底部标签栏 -->
    <CustomTabBar />
  </view>
</template>

<style scoped>
/* 基础容器样式 */
.promotion-container {
  width: 100%;
  --td-grid-item-padding: 0;
  padding-bottom: env(safe-area-inset-bottom);
}

.sticky-header {
  position: sticky;
  top: 0; /* 适配导航栏高度，默认44px */
  z-index: 998;
  background-color: white; /* 与页面背景一致，避免穿透 */
  border-bottom: 1rpx solid #f0f2f5;
}

/* 顶部banner */
.promotion-banner {
  position: relative;
  width: 100%;
}

.banner-img {
  width: 100%;
  height: auto;
}

.banner-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: linear-gradient(45deg, rgba(255, 126, 179, 0.7), rgba(100, 181, 246, 0.7));
  color: white;
}

.banner-title {
  font-size: 36rpx;
  font-weight: 600;
  margin-bottom: 12rpx;
}

.banner-desc {
  font-size: 24rpx;
  opacity: 0.9;
}

.promotion-list {
  display: flex;
  flex-direction: column;
  gap: 20rpx;
  padding: 16rpx;
}
</style>
