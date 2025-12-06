<script setup lang="ts">
import { useTabBarStore } from '@/stores/tabBarStore'
import { computed } from 'vue'

interface TabItem {
  value: string
  label: string
  icon: string
  pagePath?: string
  badgeProps?: Record<string, any>
}

const list: TabItem[] = [
  {
    value: 'pages/camera/index',
    label: '拍照识别',
    icon: 'camera',
    pagePath: '/pages/camera/index',
  },
  {
    value: 'pages/cookbook/index',
    label: '查找食谱',
    icon: 'book',
    pagePath: '/pages/cookbook/index',
  },
  {
    value: 'pages/product/index',
    label: '购买食材',
    icon: 'app',
    pagePath: '/pages/product/index',
  },
  {
    value: 'pages/cart/index',
    label: '购物清单', // 扩展为4字，更完整
    icon: 'cart',
    pagePath: '/pages/cart/index',
  },
  {
    value: 'pages/profile/index',
    label: '个人中心', // 替换为4字，更正式
    icon: 'user',
    pagePath: '/pages/profile/index',
  },
]

const tabBarStore = useTabBarStore()

// 使用 computed 双向绑定 currentValue 到 store
const currentValue = computed({
  get: () => tabBarStore.currentTab,
  set: (val) => {
    tabBarStore.setCurrentTab(val)
  },
})

function handleChange(key: { value: string }) {
  const selectedItem = list.find(item => item.value === key.value)
  tabBarStore.setCurrentTab(key.value)

  if (selectedItem && selectedItem.pagePath) {
    uni.switchTab({
      url: selectedItem.pagePath,
      fail: (err) => {
        console.error('页面跳转失败:', err)
        uni.redirectTo({
          url: selectedItem.pagePath || '',
        })
      },
    })
  }
}
</script>

<template>
  <t-tab-bar
    :value="currentValue"
    @change="handleChange"
  >
    <t-tab-bar-item
      v-for="(item, index) in list"
      :key="index"
      :value="item.value"
      :icon="item.icon"
      :badge-props="item.badgeProps"
    >
      {{ item.label }}
    </t-tab-bar-item>
  </t-tab-bar>
</template>
