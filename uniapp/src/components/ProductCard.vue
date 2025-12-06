<!-- src/components/RecipeCard.vue -->
<script lang="ts" setup>
interface Props {
  id: string
  originalPrice: number // 对应 BigDecimal，前端通常转为 number
  discountPrice: number
  tags: string[]
  product: {
    title: string
    cover: string
  }
  discount: string
}

const props = defineProps<Props>()

const emit = defineEmits<{
  (e: 'clickItem', id: string): void
}>()

function handleClick() {
  emit('clickItem', props.id)
}
</script>

<template>
  <view class="recipe-card" @click="handleClick">
    <!-- 商品封面图 -->
    <view class="recipe-cover">
      <image :src="product.cover" mode="cover" class="cover-img" />

      <view v-if="discount" class="promo-tag-1">
        <text class="promo-text">
          {{ discount }}
        </text>
      </view>

      <!-- 促销标签（tags）展示在右上角，最多显示第一个标签 -->
      <view v-if="tags && tags.length > 0" class="promo-tag">
        <text class="promo-text">
          {{ tags[0] }}
        </text>
      </view>
    </view>

    <!-- 商品信息 -->
    <view class="recipe-info">
      <text class="recipe-title">
        {{ product.title }}
      </text>

      <!-- 价格区域 -->
      <view class="price-section">
        <view style="display: flex; align-items: center; gap: 8rpx;">
          <!-- 折扣价（高亮显示） -->
          <text v-if="discountPrice != null" class="discount-price">
            ¥{{ discountPrice.toFixed(2) }}
          </text>
          <!-- 原价（划线） -->
          <text
            v-if="originalPrice != null"
            class="original-price" :class="{
              'with-discount': discountPrice != null,
            }"
          >
            ¥{{ originalPrice.toFixed(2) }}
          </text>
        </view>
        <!-- <text class="save-amount">
          省 ¥{{ calculateSaveAmount().toFixed(1) }}
        </text> -->
      </view>
    </view>
  </view>
</template>

<style scoped>
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

/* 促销标签 */
.promo-tag {
  position: absolute;
  top: 16rpx;
  right: 16rpx;
  background-color:  #ff9f43;
  color: white;
  font-size: 20rpx;
  padding: 6rpx 12rpx;
  border-radius: 16rpx;
  font-weight: 500;
}

.promo-tag-1 {
  position: absolute;
  top: 16rpx;
  left: 16rpx;
  background-color: #ff5252;
  color: white;
  font-size: 20rpx;
  padding: 6rpx 12rpx;
  border-radius: 16rpx;
  font-weight: 500;
}

.promo-text {
  font-size: 20rpx;
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

.price-section {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12rpx;
}

.discount-price {
  font-size: 28rpx;
  color: #e53e3e;
  font-weight: bold;
}

.original-price {
  font-size: 24rpx;
  color: #a0aec0;
}

.with-discount {
  text-decoration: line-through;
}

.save-amount {
  font-size: 20rpx;
  color: #4cd964;
  background-color: #f0fdf4;
  padding: 4rpx 12rpx;
  border-radius: 16rpx;
}
</style>
