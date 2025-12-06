<script lang="ts" setup>
import { useBizRecipeApi } from '@/api'
import CustomNavbar from '@/components/CustomNavbar.vue'
import EmptyState from '@/components/EmptyState.vue'
import RecipeCard from '@/components/RecipeCard.vue'
import { onLoad } from '@dcloudio/uni-app'
import { ref } from 'vue'

const vegetable = ref<string>('')
const recipes = ref<any[]>([])
const loading = ref(true)
const error = ref(false)

onLoad((options) => {
  const veg = options?.vegetable as string
  if (!veg) {
    uni.showToast({ title: '未指定食材', icon: 'none' })
    setTimeout(() => uni.navigateBack(), 1500)
    return
  }

  vegetable.value = decodeURIComponent(veg)
  fetchRecipes(vegetable.value)
})
async function fetchRecipes(veg: string) {
  loading.value = true
  error.value = false

  const req = {
    vegetable: veg,
    oilLevel: '少油',
    speed: '快手',
    difficulty: '简单',
    region: '家常',
  }
  useBizRecipeApi().GenerateRecipes(req).then(({ data, success }) => {
    if (success) {
      recipes.value = data
    }
  }).catch((err) => {
    console.error('请求菜谱失败:', err)
    error.value = true
    uni.showToast({ title: '加载菜谱失败', icon: 'none' })
  }).finally(() => {
    loading.value = false
    error.value = false
    if (recipes.value.length === 0) {
      uni.showToast({ title: `暂无「${veg}」的菜谱`, icon: 'none' })
    }
    else {
      uni.showToast({ title: `找到 ${recipes.value.length} 个菜谱`, icon: 'success' })
    }
  })
}

function goToRecipeDetail(id: string | number) {
  console.log(id)
  uni.navigateTo({
    url: `/pages/cookbook/detail?id=${id}`,
  })
}
</script>

<template>
  <view class="cookbook-container">
    <CustomNavbar :title="`${vegetable} 的菜谱`" :show-back="true" />
    <!-- 加载中 -->
    <view v-if="loading" class="center-state">
      <text class="icon">
        ⏳
      </text>
      <text class="text">
        正在查找 {{ vegetable }} 的菜谱...
      </text>
    </view>

    <!-- 错误或空状态 -->
    <scroll-view v-else class="recipe-list" scroll-y>
      <view v-if="error" class="center-state">
        <text class="icon">
          ⚠️
        </text>
        <text class="text">
          加载失败，请重试
        </text>
        <button class="retry-btn" @click="fetchRecipes(vegetable)">
          重新加载
        </button>
      </view>

      <view v-else-if="recipes.length === 0" class="center-state">
        <text class="icon">
          🥬
        </text>
        <text class="text">
          暂无 {{ vegetable }} 的菜谱
        </text>
      </view>

      <t-pull-down-refresh
        :loading-texts="['下拉刷新', '松手刷新', '正在刷新', '刷新完成']"
        :using-custom-navbar="true"
        :success-duration="600"
      >
        <!-- 空状态 -->
        <EmptyState
          v-if="recipes.length === 0"
          icon="🥗"
          title="暂无该分类的菜谱哦～"
          description="换个分类试试吧"
        />

        <t-grid
          v-else
          :border="false"
          :column="2"
          :gutter="16"
          custom-style="padding: 16rpx;"
        >
          <t-grid-item v-for="recipe in recipes" :key="recipe.id">
            <RecipeCard
              v-bind="recipe"
              style="width: 100%;"
              @click="goToRecipeDetail"
            />
          </t-grid-item>
        </t-grid>

        <!-- <view v-if="loading" class="load-more">
          <text class="loading-text">
            加载中...
          </text>
        </view>
        <view v-if="noMore && !loading" class="load-more">
          <text class="no-more-text">
            没有更多菜谱啦～
          </text>
        </view> -->

        <view style="height: 150rpx;" />
      </t-pull-down-refresh>
      <!-- 菜谱列表 -->
      <!-- <view v-for="(item, index) in recipes" v-else :key="index" class="recipe-item" @click="viewRecipeDetail(item)">
        <view class="recipe-name">
          {{ item.author }}
        </view>
        <view v-if="item.description" class="recipe-desc">
          {{ item.description }}
        </view>
        <view class="recipe-meta">
          <text>⏱️ {{ item.duration }}</text>
          <text v-if="item.difficulty">
            ｜难度：{{ item.difficulty }}
          </text>
        </view>
      </view> -->
    </scroll-view>
  </view>
</template>

<style scoped>
.cookbook-container {
  width: 100vw;
  padding-bottom: env(safe-area-inset-bottom);
}

.center-state {
  text-align: center;
  padding: 200rpx 40rpx;
}

.icon {
  font-size: 80rpx;
  display: block;
  margin-bottom: 30rpx;
  color: #a0aec0;
}

.text {
  font-size: 28rpx;
  color: #718096;
  display: block;
  margin-bottom: 40rpx;
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

.recipe-list {
  padding: 20rpx;
}

.recipe-item {
  background: white;
  border-radius: 20rpx;
  padding: 30rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
}

.recipe-name {
  font-size: 32rpx;
  font-weight: bold;
  color: #2d3748;
  margin-bottom: 16rpx;
}

.recipe-desc {
  font-size: 26rpx;
  color: #718096;
  line-height: 1.5;
  margin-bottom: 16rpx;
}

.recipe-meta {
  font-size: 24rpx;
  color: #a0aec0;
  display: flex;
  gap: 20rpx;
}
</style>
