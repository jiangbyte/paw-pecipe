<!-- src/components/RecipeCard.vue -->
<script lang="ts" setup>
// 定义 props
interface Props {
  id: string
  title: string
  cover: string
  author: string
  avatar: string
  duration: number
  difficulty: string
  likes: number | null
}

const props = defineProps<Props>()

// 定义 emits
const emit = defineEmits<{
  (e: 'clickItem', id: string): void
  (e: 'like', id: string): void
}>()

// 点击卡片
function handleClick() {
  emit('clickItem', props.id)
}

// 点赞
function onLike() {
  emit('like', props.id)
}

// 获取难度样式
function getDifficultyClass(difficulty: string) {
  switch (difficulty) {
    case '简单': return 'difficulty-easy'
    case '中等': return 'difficulty-medium'
    case '较难': return 'difficulty-hard'
    default: return ''
  }
}
</script>

<template>
  <view class="recipe-card" @click="handleClick">
    <!-- 菜谱封面图 -->
    <view class="recipe-cover">
      <image :src="cover" mode="cover" class="cover-img" />
      <!-- 时长标签 -->
      <view class="duration-tag">
        <text class="duration-icon">
          ⏱️
        </text>
        <text class="duration-text">
          {{ duration }}
        </text>
        <text class="duration-text">
          分钟
        </text>
      </view>
      <!-- 难度标签 -->
      <view class="difficulty-tag" :class="getDifficultyClass(difficulty)">
        <text class="difficulty-text">
          {{ difficulty }}
        </text>
      </view>
    </view>

    <!-- 菜谱信息 -->
    <view class="recipe-info">
      <text class="recipe-title">
        {{ title }}
      </text>

      <view class="recipe-meta">
        <view class="author-info">
          <image :src="avatar" mode="cover" class="author-avatar" />
          <text class="author-name">
            {{ author }}
          </text>
        </view>

        <view class="like-container" @click.stop="onLike">
          <text class="like-icon">
            ❤️
          </text>
          <text class="like-count">
            {{ likes ? likes : '0' }}
          </text>
        </view>
      </view>
    </view>
  </view>
</template>

<style scoped>
/* 复用样式直接拷贝过来 */
.recipe-card {
  width: 100%;
  border-radius: 18rpx;
  overflow: hidden;
  border: 1rpx solid #f0f2f5;
}

.recipe-card:active {
  transform: scale(0.98);
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.03);
}

.recipe-cover {
  position: relative;
  width: 100%;
  aspect-ratio: 4/3;
  overflow: hidden;
}

.cover-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.duration-tag {
  position: absolute;
  top: 16rpx;
  left: 16rpx;
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  font-size: 20rpx;
  padding: 6rpx 12rpx;
  border-radius: 16rpx;
  display: flex;
  align-items: center;
  gap: 6rpx;
}

.duration-icon {
  font-size: 22rpx;
}

.difficulty-tag {
  position: absolute;
  top: 16rpx;
  right: 16rpx;
  font-size: 20rpx;
  padding: 6rpx 12rpx;
  border-radius: 16rpx;
  color: white;
  font-weight: 500;
}

.difficulty-easy {
  background-color: #4cd964;
}

.difficulty-medium {
  background-color: #ffc107;
}

.difficulty-hard {
  background-color: #ff5252;
}

.recipe-info {
  padding: 16rpx;
}

.recipe-title {
  font-size: 30rpx;
  font-weight: 600;
  color: #2d3748;
  margin-bottom: 12rpx;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.recipe-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.author-info {
  display: flex;
  align-items: center;
  gap: 8rpx;
}

.author-avatar {
  width: 48rpx;
  height: 48rpx;
  border-radius: 50%;
  object-fit: cover;
}

.author-name {
  font-size: 24rpx;
  color: #718096;
  white-space: nowrap;
  max-width: 160rpx;
  overflow: hidden;
  text-overflow: ellipsis;
}

.like-container {
  display: flex;
  align-items: center;
  gap: 6rpx;
}

.like-icon {
  font-size: 22rpx;
  color: #ff5252;
}

.like-count {
  font-size: 20rpx;
  color: #718096;
}
</style>
