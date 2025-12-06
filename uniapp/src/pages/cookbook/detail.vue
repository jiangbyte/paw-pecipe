<script lang="ts" setup>
import { useBizRecipeApi } from '@/api'
import CustomNavbar from '@/components/CustomNavbar.vue'
import RelatedIngredients from '@/components/RelatedIngredients.vue'
import { onLoad, onShareAppMessage } from '@dcloudio/uni-app'
import { ref } from 'vue'

// 菜谱详情数据
const recipeDetail = ref()

// 状态管理
const isCollected = ref(false)
const isLiked = ref(false)
const activeTab = ref('steps') // steps/ingredients/tips
const currentStep = ref(1)

onLoad((options) => {
  const id = options?.id
  console.log('菜谱ID:', id)

  useBizRecipeApi().GetBizRecipe(id).then(({ data, success }) => {
    if (success) {
      recipeDetail.value = data
    }
  })

  // // 模拟从缓存获取收藏状态
  // const collectedRecipes = uni.getStorageSync('collectedRecipes') || []
  // isCollected.value = collectedRecipes.includes(id)

  // // 模拟从缓存获取点赞状态
  // const likedRecipes = uni.getStorageSync('likedRecipes') || []
  // isLiked.value = likedRecipes.includes(id)
})

// 切换标签
function switchTab(tab: string) {
  activeTab.value = tab
}

// // 点赞功能
// function handleLike() {
//   if (isLiked.value) {
//     recipeDetail.value.likes--
//   }
//   else {
//     recipeDetail.value.likes++
//   }
//   isLiked.value = !isLiked.value

//   // 保存到缓存
//   let likedRecipes = uni.getStorageSync('likedRecipes') || []
//   if (isLiked.value) {
//     likedRecipes.push(recipeDetail?.value.id)
//   }
//   else {
//     likedRecipes = likedRecipes.filter((id: number) => id !== recipeDetail?.value.id)
//   }
//   uni.setStorageSync('likedRecipes', likedRecipes)
// }

// // 收藏功能
// function handleCollect() {
//   isCollected.value = !isCollected.value

//   // 保存到缓存
//   let collectedRecipes = uni.getStorageSync('collectedRecipes') || []
//   if (isCollected.value) {
//     collectedRecipes.push(recipeDetail?.value.id)
//     uni.showToast({ title: '收藏成功', icon: 'success' })
//   }
//   else {
//     collectedRecipes = collectedRecipes.filter((id: number) => id !== recipeDetail?.value.id)
//     uni.showToast({ title: '取消收藏', icon: 'none' })
//   }
//   uni.setStorageSync('collectedRecipes', collectedRecipes)
// }

// 分享功能
onShareAppMessage(() => {
  return {
    title: `推荐菜谱：${recipeDetail?.value.title}`,
    path: `/pages/recipeDetail/index?id=${recipeDetail?.value.id}`,
    imageUrl: recipeDetail.value.cover,
  }
})
</script>

<template>
  <view class="recipe-detail-container">
    <!-- 导航栏 -->
    <CustomNavbar title="菜谱详情" :show-back="true" />

    <!-- 菜谱封面图 -->
    <view class="recipe-cover">
      <!-- 替换原来的 <image> -->
      <view
        class="cover-img"
        :style="{ backgroundImage: `url(${recipeDetail?.cover})` }"
      />

      <!-- 封面信息叠加层 -->
      <view class="cover-info">
        <text class="recipe-title">
          {{ recipeDetail?.title }}
        </text>

        <view class="recipe-meta">
          <view class="meta-item">
            <text class="meta-icon">
              ⏱️
            </text>
            <text class="meta-text">
              {{ recipeDetail?.duration }}
            </text>
            <text class="meta-text">
              分钟
            </text>
          </view>
          <view class="meta-item">
            <text class="meta-icon">
              📊
            </text>
            <text class="meta-text">
              {{ recipeDetail?.difficulty }}
            </text>
          </view>
          <view class="meta-item">
            <text class="meta-icon">
              🍽️
            </text>
            <text class="meta-text">
              {{ recipeDetail?.categoryName }}
            </text>
          </view>
        </view>
      </view>
    </view>

    <!-- 作者信息 -->
    <view class="author-info">
      <image :src="recipeDetail?.avatar" mode="cover" class="author-avatar" />
      <view class="author-meta">
        <text class="author-name">
          {{ recipeDetail?.author }}
        </text>
        <!-- <text class="author-desc">
          美食达人 · 家常菜专家
        </text> -->
      </view>
      <view class="interaction-buttons">
        <view class="like-button">
          <text class="like-icon" :class="{ liked: isLiked }">
            ❤️
          </text>
          <text class="like-count">
            {{ recipeDetail?.likes }}
          </text>
        </view>
        <view class="collect-button">
          <text class="collect-icon" :class="{ collected: isCollected }">
            ⭐
          </text>
          <text class="collect-count">
            {{ recipeDetail?.collects }}
          </text>
        </view>
        <button class="share-button" open-type="share">
          <text class="share-icon">
            📤
          </text>
          <text class="share-text">
            分享
          </text>
        </button>
      </view>
    </view>

    <!-- 菜谱描述 -->
    <view class="recipe-description">
      <text class="desc-text">
        {{ recipeDetail?.description }}
      </text>
    </view>

    <!-- 内容标签切换 -->
    <view class="content-tabs">
      <view
        class="tab-item"
        :class="{ active: activeTab === 'ingredients' }"
        @click="switchTab('ingredients')"
      >
        <text class="tab-text">
          食材清单
        </text>
      </view>
      <view
        class="tab-item"
        :class="{ active: activeTab === 'steps' }"
        @click="switchTab('steps')"
      >
        <text class="tab-text">
          烹饪步骤
        </text>
      </view>
      <view
        class="tab-item"
        :class="{ active: activeTab === 'tips' }"
        @click="switchTab('tips')"
      >
        <text class="tab-text">
          烹饪技巧
        </text>
      </view>
    </view>

    <!-- 食材清单 -->
    <view v-if="activeTab === 'ingredients'" class="ingredients-content">
      <view class="ingredients-list">
        <view v-for="(item, index) in recipeDetail?.ingredients" :key="index" class="ingredient-item">
          <text class="ingredient-icon">
            🥬
          </text>
          <text class="ingredient-name">
            {{ item.name }}
          </text>
          <text class="ingredient-amount">
            {{ item.amount }}
          </text>
        </view>
      </view>

      <RelatedIngredients
        :recipe-id="recipeDetail?.id"
        title="烹饪食材推荐"
        subtitle="食材新鲜直达，烹饪更省心"
        :limit="6"
        layout="horizontal"
        style="margin-top: 24rpx;"
      />
    </view>

    <!-- 烹饪步骤 -->
    <view v-if="activeTab === 'steps'" class="steps-content">
      <!-- 步骤导航（小屏幕隐藏，大屏幕显示） -->
      <view class="steps-nav" :style="{ height: `${recipeDetail?.steps.length * 60 + 20}rpx` }">
        <view
          v-for="step in recipeDetail?.steps"
          :key="step.id"
          class="step-nav-item"
          :class="{ active: currentStep === step.id }"
        >
          <text class="step-nav-number">
            {{ step.id }}
          </text>
        </view>
      </view>

      <!-- 步骤详情 -->
      <view class="steps-list">
        <view
          v-for="step in recipeDetail?.steps"
          :key="step.id"
          class="step-item"
          :class="`step-item-${step.id}`"
        >
          <view class="step-number">
            <text class="number-text">
              {{ step.id }}
            </text>
          </view>
          <view class="step-content">
            <text class="step-desc">
              {{ step.desc }}
            </text>
            <!-- <image :src="step.image" mode="widthFix" class="step-image" /> -->
          </view>
        </view>
      </view>
    </view>

    <!-- 烹饪技巧 -->
    <view v-if="activeTab === 'tips'" class="tips-content">
      <view class="tips-list">
        <view v-for="(tip, index) in recipeDetail?.tips" :key="index" class="tip-item">
          <text class="tip-icon">
            💡
          </text>
          <text class="tip-text">
            {{ tip }}
          </text>
        </view>
      </view>
    </view>
  </view>
</template>

<style scoped>
/* 基础容器样式 */
.recipe-detail-container {
  display: flex;
  flex-direction: column;
  padding-bottom: env(safe-area-inset-bottom);
}

/* 菜谱封面图 */
.recipe-cover {
  position: relative;
  width: 100vw;
}

.cover-img {
  width: 100vw;
  height: 300rpx;
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
}

.cover-info {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 30rpx 20rpx;
  background: linear-gradient(transparent, rgba(0, 0, 0, 0.7));
  color: white;
}

.recipe-title {
  font-size: 36rpx;
  font-weight: 600;
  margin-bottom: 16rpx;
  display: block;
}

.recipe-meta {
  display: flex;
  gap: 24rpx;
  flex-wrap: wrap;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 8rpx;
  font-size: 22rpx;
}

.meta-icon {
  font-size: 24rpx;
}

/* 作者信息 */
.author-info {
  display: flex;
  align-items: center;
  padding: 20rpx;
  background-color: white;
  margin: 20rpx;
  border-radius: 24rpx;
  /* box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05); */
}

.author-avatar {
  width: 80rpx;
  height: 80rpx;
  border-radius: 50%;
  object-fit: cover;
  margin-right: 20rpx;
}

.author-meta {
  flex: 1;
}

.author-name {
  font-size: 28rpx;
  font-weight: 600;
  color: #2d3748;
  display: block;
  margin-bottom: 4rpx;
}

.author-desc {
  font-size: 22rpx;
  color: #718096;
}

.interaction-buttons {
  display: flex;
  align-items: center;
  gap: 20rpx;
}

.like-button, .collect-button {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4rpx;
}

.like-icon, .collect-icon {
  font-size: 32rpx;
  color: #a0aec0;
}

.like-icon.liked {
  color: #ff5252;
  animation: heartBeat 0.5s ease;
}

.collect-icon.collected {
  color: #ffc107;
  animation: starShine 0.5s ease;
}

.like-count, .collect-count {
  font-size: 20rpx;
  color: #718096;
}

.share-button {
  background: linear-gradient(135deg, #ff7eb3, #64b5f6);
  color: white;
  border: none;
  border-radius: 32rpx;
  padding: 0 20rpx;
  font-size: 22rpx;
  display: flex;
  align-items: center;
  gap: 8rpx;
  height: 56rpx;
  line-height: 56rpx;
}

.share-icon {
  font-size: 24rpx;
}

/* 菜谱描述 */
.recipe-description {
  padding: 0 20rpx;
  margin-bottom: 20rpx;
}

.desc-text {
  font-size: 24rpx;
  color: #4a5568;
  line-height: 1.6;
}

/* 内容标签切换 */
.content-tabs {
  display: flex;
  background-color: white;
  border-radius: 24rpx;
  margin: 0 20rpx 20rpx;
  overflow: hidden;
  /* box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05); */
}

.tab-item {
  flex: 1;
  text-align: center;
  padding: 20rpx 0;
  font-size: 26rpx;
  font-weight: 500;
  color: #718096;
  position: relative;
  transition: all 0.3s ease;
}

.tab-item.active {
  color: #ff7eb3;
}

.tab-item.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 60rpx;
  height: 6rpx;
  background: linear-gradient(90deg, #ff7eb3, #64b5f6);
  border-radius: 3rpx;
}

/* 食材清单 */
.ingredients-content {
  padding: 0 20rpx 20rpx;
}

.ingredients-list {
  background-color: white;
  border-radius: 24rpx;
  padding: 24rpx;
  /* box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05); */
}

.ingredient-item {
  display: flex;
  align-items: center;
  padding: 16rpx 0;
  border-bottom: 1rpx solid #f0f2f5;
}

.ingredient-item:last-child {
  border-bottom: none;
}

.ingredient-icon {
  font-size: 32rpx;
  margin-right: 20rpx;
  width: 40rpx;
  text-align: center;
}

.ingredient-name {
  flex: 1;
  font-size: 24rpx;
  color: #2d3748;
}

.ingredient-amount {
  font-size: 22rpx;
  color: #718096;
  background-color: #f5f6f7;
  padding: 4rpx 16rpx;
  border-radius: 16rpx;
}

/* 烹饪步骤 */
.steps-content {
  padding: 0 20rpx 20rpx;
  display: flex;
  gap: 20rpx;
}

.steps-nav {
  width: 60rpx;
  background-color: white;
  border-radius: 30rpx;
  padding: 10rpx 0;
  display: none; /* 默认隐藏，大屏幕显示 */
  flex-direction: column;
  align-items: center;
  gap: 10rpx;
  /* box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05); */
}

.step-nav-item {
  width: 40rpx;
  height: 40rpx;
  border-radius: 50%;
  background-color: #f5f6f7;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 22rpx;
  color: #718096;
  font-weight: 500;
  transition: all 0.3s ease;
}

.step-nav-item.active {
  background: linear-gradient(135deg, #ff7eb3, #64b5f6);
  color: white;
}

.steps-list {
  flex: 1;
  background-color: white;
  border-radius: 24rpx;
  padding: 24rpx;
  /* box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05); */
}

.step-item {
  display: flex;
  gap: 20rpx;
  margin-bottom: 32rpx;
  position: relative;
}

.step-item:last-child {
  margin-bottom: 0;
}

/* 步骤连接线 */
.step-item::before {
  content: '';
  position: absolute;
  left: 20rpx;
  top: 40rpx;
  bottom: -16rpx;
  width: 2rpx;
  background-color: #f0f2f5;
  z-index: 0;
}

.step-item:last-child::before {
  display: none;
}

.step-number {
  width: 40rpx;
  height: 40rpx;
  border-radius: 50%;
  background: linear-gradient(135deg, #ff7eb3, #64b5f6);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 22rpx;
  font-weight: 600;
  z-index: 1;
}

.step-number.current {
  /* box-shadow: 0 0 0 10rpx rgba(255, 126, 179, 0.1); */
}

.step-content {
  flex: 1;
}

.step-desc {
  font-size: 24rpx;
  color: #2d3748;
  line-height: 1.6;
  margin-bottom: 16rpx;
  display: block;
}

.step-image {
  width: 100%;
  height: auto;
  border-radius: 16rpx;
}

/* 烹饪技巧 */
.tips-content {
  padding: 0 20rpx 20rpx;
}

.tips-list {
  background-color: white;
  border-radius: 24rpx;
  padding: 24rpx;
  /* box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05); */
}

.tip-item {
  display: flex;
  align-items: flex-start;
  gap: 16rpx;
  padding: 16rpx 0;
  border-bottom: 1rpx solid #f0f2f5;
}

.tip-item:last-child {
  border-bottom: none;
}

.tip-icon {
  font-size: 28rpx;
  color: #ff9f43;
  margin-top: 4rpx;
}

.tip-text {
  flex: 1;
  font-size: 24rpx;
  color: #4a5568;
  line-height: 1.6;
}

/* 动画效果 */
@keyframes heartBeat {
  0% { transform: scale(1); }
  50% { transform: scale(1.3); }
  100% { transform: scale(1); }
}

@keyframes starShine {
  0% { transform: scale(1); }
  50% { transform: scale(1.3); }
  100% { transform: scale(1); }
}
</style>
