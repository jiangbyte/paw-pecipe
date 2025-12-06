<script lang="ts" setup>
import { defineProps, onMounted, ref } from 'vue'

// 定义组件Props（外部传入）
const props = defineProps<{
  recipeId: number | string // 食谱ID（用于请求相关食材）
  showTitle?: boolean // 是否显示标题区，默认true
  title?: string // 自定义标题，默认"相关食材推荐"
  subtitle?: string // 自定义副标题，默认"烹饪必备，一键选购"
  limit?: number // 最大展示数量，默认6
  layout?: 'horizontal' | 'vertical' // 布局方式，默认横向滚动
}>()

// 默认配置
const defaultConfig = {
  showTitle: true,
  title: '相关食材推荐',
  subtitle: '烹饪必备，一键选购',
  limit: 6,
  layout: 'horizontal' as const,
}

// 合并配置
const config = { ...defaultConfig, ...props }

// 状态管理
const ingredients = ref<any[]>([])
const loading = ref(true)
const error = ref('')

// 模拟API请求：根据食谱ID获取相关食材推荐
async function fetchRelatedIngredients() {
  try {
    loading.value = true
    error.value = ''

    // 实际项目中替换为真实接口请求：
    // const response = await axios.get(`/api/recipes/${config.recipeId}/related-ingredients`)
    // ingredients.value = response.data.data.slice(0, config.limit)

    // 模拟接口延迟和返回数据
    await new Promise(resolve => setTimeout(resolve, 600))

    // 模拟根据不同食谱ID返回不同相关食材（示例数据）
    const mockIngredients = getMockIngredientsByRecipeId(config.recipeId)
    ingredients.value = mockIngredients.slice(0, config.limit)
  }
  catch (err) {
    error.value = '食材推荐加载失败，请稍后重试'
    console.error('获取相关食材失败:', err)
  }
  finally {
    loading.value = false
  }
}

// 根据食谱ID返回对应的相关食材（模拟真实接口的关联逻辑）
function getMockIngredientsByRecipeId(recipeId: number | string) {
  // 不同食谱ID返回不同的相关食材（示例：番茄炒蛋、草莓奶冻、清炒时蔬等）
  const ingredientMap: Record<string, any[]> = {
    // 番茄炒蛋（ID=1）相关食材
    1: [
      {
        id: 101,
        name: '新鲜番茄',
        cover: 'https://picsum.photos/seed/tomato/400/300',
        spec: '500g/份',
        tag: '必备',
        sales: 2356,
        isRecipeIngredient: true, // 是否为食谱必填食材
      },
      {
        id: 102,
        name: '土鸡蛋',
        cover: 'https://picsum.photos/seed/egg/400/300',
        spec: '10枚/盒',
        tag: '优选',
        sales: 1892,
        isRecipeIngredient: true,
      },
      {
        id: 103,
        name: '新鲜小葱',
        cover: 'https://picsum.photos/seed/scallion/400/300',
        spec: '100g/份',
        tag: '调味',
        sales: 1563,
        isRecipeIngredient: true,
      },
      {
        id: 104,
        name: '食用盐',
        cover: 'https://picsum.photos/seed/salt/400/300',
        spec: '200g/袋',
        tag: '基础',
        sales: 987,
        isRecipeIngredient: true,
      },
      {
        id: 107,
        name: '鸡精',
        cover: 'https://picsum.photos/seed/chickenPowder/400/300',
        spec: '100g/袋',
        tag: '提鲜',
        sales: 543,
        isRecipeIngredient: false, // 推荐搭配食材
      },
      {
        id: 108,
        name: '生抽',
        cover: 'https://picsum.photos/seed/soySauce/400/300',
        spec: '500ml/瓶',
        tag: '增香',
        sales: 765,
        isRecipeIngredient: false,
      },
    ],
    // 草莓奶冻（ID=2）相关食材（示例，可扩展）
    2: [
      {
        id: 201,
        name: '新鲜草莓',
        cover: 'https://picsum.photos/seed/strawberry/400/300',
        spec: '300g/份',
        tag: '主料',
        sales: 1987,
        isRecipeIngredient: true,
      },
      {
        id: 202,
        name: '纯牛奶',
        cover: 'https://picsum.photos/seed/milk/400/300',
        spec: '500ml/盒',
        tag: '必备',
        sales: 2456,
        isRecipeIngredient: true,
      },
      {
        id: 203,
        name: '吉利丁片',
        cover: 'https://picsum.photos/seed/gelatin/400/300',
        spec: '10片/包',
        tag: '凝固',
        sales: 876,
        isRecipeIngredient: true,
      },
      {
        id: 204,
        name: '白砂糖',
        cover: 'https://picsum.photos/seed/sugar/400/300',
        spec: '300g/瓶',
        tag: '调味',
        sales: 1234,
        isRecipeIngredient: true,
      },
    ],
  }

  // 如果没有对应食谱的食材，返回通用食材推荐
  return ingredientMap[recipeId.toString()] || [
    {
      id: 999,
      name: '通用食用油',
      cover: 'https://picsum.photos/seed/oil/400/300',
      spec: '1L/瓶',
      tag: '必备',
      sales: 3210,
      isRecipeIngredient: false,
    },
  ]
}

// 组件挂载时请求数据
onMounted(() => {
  fetchRelatedIngredients()
})

// 跳转至食材详情页
function goToIngredientDetail(ingredient: any) {
  // 跳转至食材详情页（携带食材ID）
  uni.navigateTo({
    url: `/pages/ingredient/detail?id=${ingredient.id}&name=${encodeURIComponent(ingredient.name)}`,
  })
}

// 获取标签样式类
function getTagClass(tag: string) {
  const tagMap: Record<string, string> = {
    必备: 'tag-essential',
    优选: 'tag-premium',
    调味: 'tag-seasoning',
    主料: 'tag-main',
    基础: 'tag-basic',
    提鲜: 'tag-fresh',
    增香: 'tag-flavor',
    凝固: 'tag-set',
  }
  return tagMap[tag] || 'tag-default'
}
</script>

<template>
  <view class="related-ingredients-component">
    <!-- 标题区 -->
    <view v-if="config.showTitle" class="component-header">
      <text class="header-title">
        {{ config.title }}
      </text>
      <text class="header-subtitle">
        {{ config.subtitle }}
      </text>
    </view>

    <!-- 加载中 -->
    <view v-if="loading" class="loading-state">
      <text class="loading-text">
        加载中...
      </text>
    </view>

    <!-- 加载失败 -->
    <view v-if="error && !loading" class="error-state">
      <text class="error-text">
        {{ error }}
      </text>
      <button class="retry-btn" @click="fetchRelatedIngredients">
        重试
      </button>
    </view>

    <!-- 食材推荐列表 - 横向滚动布局 -->
    <scroll-view
      v-if="ingredients.length > 0 && !loading && !error && config.layout === 'horizontal'"
      class="ingredients-scroll horizontal-layout"
      scroll-x
      show-scrollbar="false"
    >
      <view
        v-for="ingredient in ingredients"
        :key="ingredient.id"
        class="ingredient-card"
        @click="goToIngredientDetail(ingredient)"
      >
        <!-- 食材图片 -->
        <view class="ingredient-cover">
          <image :src="ingredient.cover" mode="cover" class="cover-img" />
          <!-- 标签 -->
          <view class="ingredient-tag" :class="getTagClass(ingredient.tag)">
            <text class="tag-text">
              {{ ingredient.tag }}
            </text>
          </view>
          <!-- 是否为食谱必填食材标记 -->
          <view v-if="ingredient.isRecipeIngredient" class="recipe-required-tag">
            <text class="required-text">
              食谱必备
            </text>
          </view>
        </view>

        <!-- 食材信息 -->
        <view class="ingredient-info">
          <text class="ingredient-name">
            {{ ingredient.name }}
          </text>
          <text class="ingredient-spec">
            {{ ingredient.spec }}
          </text>
          <view class="sales-info">
            <text class="sales-icon">
              🔥
            </text>
            <text class="sales-text">
              已售 {{ ingredient.sales }}+
            </text>
          </view>
        </view>

        <!-- 点击提示 -->
        <view class="click-indicator">
          <text class="indicator-text">
            查看详情
          </text>
          <text class="arrow-icon">
            →
          </text>
        </view>
      </view>
    </scroll-view>

    <!-- 食材推荐列表 - 纵向网格布局 -->
    <view
      v-if="ingredients.length > 0 && !loading && !error && config.layout === 'vertical'"
      class="ingredients-grid vertical-layout"
    >
      <view
        v-for="ingredient in ingredients"
        :key="ingredient.id"
        class="ingredient-card"
        @click="goToIngredientDetail(ingredient)"
      >
        <view class="ingredient-cover">
          <image :src="ingredient.cover" mode="cover" class="cover-img" />
          <view class="ingredient-tag" :class="getTagClass(ingredient.tag)">
            <text class="tag-text">
              {{ ingredient.tag }}
            </text>
          </view>
          <view v-if="ingredient.isRecipeIngredient" class="recipe-required-tag">
            <text class="required-text">
              食谱必备
            </text>
          </view>
        </view>
        <view class="ingredient-info">
          <text class="ingredient-name">
            {{ ingredient.name }}
          </text>
          <text class="ingredient-spec">
            {{ ingredient.spec }}
          </text>
          <view class="sales-info">
            <text class="sales-icon">
              🔥
            </text>
            <text class="sales-text">
              已售 {{ ingredient.sales }}+
            </text>
          </view>
        </view>
        <view class="click-indicator">
          <text class="indicator-text">
            查看详情
          </text>
          <text class="arrow-icon">
            →
          </text>
        </view>
      </view>
    </view>

    <!-- 无数据状态 -->
    <view v-if="ingredients.length === 0 && !loading && !error" class="empty-state">
      <text class="empty-icon">
        🥬
      </text>
      <text class="empty-text">
        暂无相关食材推荐
      </text>
    </view>
  </view>
</template>

<style scoped>
/* 组件容器 */
.related-ingredients-component {
  width: 100%;
}

/* 标题区 */
.component-header {
  margin-bottom: 16rpx;
}

.header-title {
  font-size: 28rpx;
  font-weight: 600;
  color: #2d3748;
  display: block;
  margin-bottom: 4rpx;
}

.header-subtitle {
  font-size: 20rpx;
  color: #718096;
}

/* 加载状态 */
.loading-state {
  text-align: center;
  padding: 40rpx 0;
}

.loading-text {
  font-size: 24rpx;
  color: #a0aec0;
}

/* 错误状态 */
.error-state {
  text-align: center;
  padding: 40rpx 0;
}

.error-text {
  font-size: 24rpx;
  color: #ff5252;
  margin-bottom: 20rpx;
  display: block;
}

.retry-btn {
  background-color: #f5f6f7;
  color: #4a5568;
  border: none;
  border-radius: 24rpx;
  padding: 0 24rpx;
  font-size: 22rpx;
  height: 56rpx;
  line-height: 56rpx;
}

/* 横向滚动布局 */
.ingredients-scroll.horizontal-layout {
  white-space: nowrap;
  padding: 8rpx 0;
}

/* 纵向网格布局 */
.ingredients-grid.vertical-layout {
  display: flex;
  flex-wrap: wrap;
  gap: 20rpx;
}

/* 食材卡片通用样式 */
.ingredient-card {
  background-color: white;
  border-radius: 24rpx;
  overflow: hidden;
  box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  cursor: pointer;
}

/* 横向布局卡片 */
.horizontal-layout .ingredient-card {
  width: 240rpx;
  margin-right: 16rpx;
  display: inline-block;
  vertical-align: top;
}

/* 纵向布局卡片 */
.vertical-layout .ingredient-card {
  width: calc(50% - 10rpx);
}

/* 卡片交互效果 */
.ingredient-card:active {
  transform: scale(0.98);
  box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.03);
}

/* 食材图片 */
.ingredient-cover {
  position: relative;
  width: 100%;
  height: 180rpx;
}

.cover-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 标签样式 */
.ingredient-tag {
  position: absolute;
  top: 12rpx;
  left: 12rpx;
  padding: 4rpx 12rpx;
  border-radius: 16rpx;
  font-size: 20rpx;
  font-weight: 600;
  color: white;
}

/* 标签颜色体系 */
.tag-essential { background-color: #ff7eb3; } /* 必备 - 粉色 */
.tag-premium { background-color: #64b5f6; } /* 优选 - 蓝色 */
.tag-seasoning { background-color: #ff9f43; } /* 调味 - 橙色 */
.tag-main { background-color: #4cd964; } /* 主料 - 绿色 */
.tag-basic { background-color: #9c27b0; } /* 基础 - 紫色 */
.tag-fresh { background-color: #00bcd4; } /* 提鲜 - 青色 */
.tag-flavor { background-color: #ff5252; } /* 增香 - 红色 */
.tag-set { background-color: #795548; } /* 凝固 - 棕色 */
.tag-default { background-color: #a0aec0; } /* 默认 - 灰色 */

/* 食谱必备标记 */
.recipe-required-tag {
  position: absolute;
  bottom: 12rpx;
  left: 12rpx;
  background-color: rgba(255, 255, 255, 0.9);
  padding: 4rpx 12rpx;
  border-radius: 16rpx;
  font-size: 18rpx;
  font-weight: 500;
  color: #ff7eb3;
}

/* 食材信息 */
.ingredient-info {
  padding: 16rpx;
}

.ingredient-name {
  font-size: 24rpx;
  font-weight: 600;
  color: #2d3748;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
  margin-bottom: 8rpx;
}

.ingredient-spec {
  font-size: 20rpx;
  color: #718096;
  margin-bottom: 12rpx;
  display: block;
}

.sales-info {
  display: flex;
  align-items: center;
  gap: 6rpx;
}

.sales-icon {
  font-size: 20rpx;
}

.sales-text {
  font-size: 18rpx;
  color: #a0aec0;
}

/* 点击提示 */
.click-indicator {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 8rpx 0;
  color: #64b5f6;
  font-size: 20rpx;
}

.arrow-icon {
  margin-left: 6rpx;
  font-size: 22rpx;
  animation: arrowPulse 1.5s infinite;
}

/* 箭头呼吸动画 */
@keyframes arrowPulse {
  0% { transform: translateX(0); }
  50% { transform: translateX(6rpx); }
  100% { transform: translateX(0); }
}

/* 无数据状态 */
.empty-state {
  text-align: center;
  padding: 60rpx 0;
}

.empty-icon {
  font-size: 80rpx;
  margin-bottom: 20rpx;
  display: block;
}

.empty-text {
  font-size: 24rpx;
  color: #a0aec0;
}

/* 响应式适配 */
@media (min-width: 750rpx) {
  .vertical-layout .ingredient-card {
    width: calc(33.333% - 14rpx);
  }

  .ingredient-cover {
    height: 220rpx;
  }
}

/* 深色模式适配 */
@media (prefers-color-scheme: dark) {
  .ingredient-card {
    background-color: #2d3748;
  }

  .header-title, .ingredient-name {
    color: #f7fafc;
  }

  .header-subtitle, .ingredient-spec, .sales-text, .empty-text, .loading-text {
    color: #a0aec0;
  }

  .recipe-required-tag {
    background-color: rgba(45, 55, 72, 0.9);
  }
}
</style>
