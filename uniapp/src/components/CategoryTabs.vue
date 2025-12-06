<!-- src/components/CategoryTabs.vue -->
<script lang="ts" setup>
// 定义 props 和 emits
interface TabItem {
  value: string
  text: string
}

interface Props {
  modelValue: string // 当前选中项（支持 v-model）
  tabList: TabItem[]
}

const props = defineProps<Props>()
const emit = defineEmits<{
  (e: 'update:modelValue', value: string): void
  (e: 'change', value: string): void
}>()

function handleClick(value: string) {
  if (value === props.modelValue)
    return
  emit('update:modelValue', value)
  emit('change', value)
}
</script>

<template>
  <view class="tabs-container">
    <scroll-view
      scroll-x
      class="tabs-scroll"
      :show-scrollbar="false"
    >
      <view
        v-for="tab in tabList"
        :key="tab.value"
        class="tab-item"
        :class="{ active: modelValue === tab.value }"
        @click="handleClick(tab.value)"
      >
        <text class="tab-text">
          {{ tab.text }}
        </text>
        <view v-if="modelValue === tab.value" class="tab-active-line" />
      </view>
    </scroll-view>
  </view>
</template>

<style scoped>
.tabs-container {
  background-color: white;
  padding: 0 20rpx;
  border-bottom: 1rpx solid #f0f2f5;
}

.tabs-scroll {
  white-space: nowrap;
  padding: 16rpx 0;
}

.tab-item {
  display: inline-block;
  padding: 0 32rpx;
  position: relative;
}

.tab-text {
  font-size: 28rpx;
  font-weight: 500;
  color: #718096;
  line-height: 56rpx;
}

.tab-item.active .tab-text {
  color: #ff7eb3;
}

.tab-active-line {
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 32rpx;
  height: 6rpx;
  background: linear-gradient(90deg, #ff7eb3, #64b5f6);
  border-radius: 3rpx;
}
</style>
