<!-- src/components/EmptyState.vue -->
<script lang="ts" setup>
interface Props {
  icon?: string // 默认图标 emoji 或 Unicode
  title?: string // 主标题
  description?: string // 副标题/说明
}

defineProps<Props>()
</script>

<template>
  <view class="empty-state">
    <!-- 图标区域：优先使用 slot，否则用 props.icon -->
    <slot name="icon">
      <text v-if="icon" class="empty-icon">
        {{ icon }}
      </text>
      <view v-else class="empty-icon">
        🥗
      </view>
    </slot>

    <!-- 标题 -->
    <slot name="title">
      <text class="empty-text">
        {{ title }}
      </text>
    </slot>

    <!-- 描述 -->
    <slot name="description">
      <text v-if="description" class="empty-desc">
        {{ description }}
      </text>
    </slot>

    <!-- 底部操作区（比如“去首页”按钮） -->
    <slot name="action" />
  </view>
</template>

<style scoped>
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 80rpx 40rpx;
  text-align: center;
  min-height: 400rpx;
}

.empty-icon {
  font-size: 80rpx;
  margin-bottom: 24rpx;
  line-height: 1;
}

.empty-text {
  font-size: 28rpx;
  color: #2d3748;
  font-weight: 500;
  margin-bottom: 12rpx;
}

.empty-desc {
  font-size: 24rpx;
  color: #a0aec0;
}
</style>
