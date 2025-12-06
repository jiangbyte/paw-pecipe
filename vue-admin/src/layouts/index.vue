<!-- <script setup lang="ts">
import { useAppStore } from '@/stores'

import LayoutHeader from './components/LayoutHeader.vue'
import LayoutSide from './components/LayoutSide.vue'
import LayoutContent from './components/LayoutContent.vue'

const appStore = useAppStore()
const { collapsed } = storeToRefs(appStore)

// 定义折叠时的宽度
const asideWidth = computed(() => collapsed.value ? '64px' : '232px')
</script>

<template>
  <t-layout class="h-screen w-screen">
    <t-aside :width="asideWidth">
      <LayoutSide />
    </t-aside>
    <t-layout>
      <t-header>
        <LayoutHeader />
      </t-header>
      <t-content>
        <LayoutContent />
      </t-content>
    </t-layout>
  </t-layout>
</template>

<style scoped>

</style> -->

<script setup lang="ts">
  import { useAppStore } from '@/stores'
  import LayoutHeader from './components/LayoutHeader.vue'
  import LayoutSide from './components/LayoutSide.vue'
  import LayoutContent from './components/LayoutContent.vue'

  const appStore = useAppStore()
  const { collapsed } = storeToRefs(appStore)

  // 定义折叠时的宽度
  const asideWidth = computed(() => (collapsed.value ? '64px' : '232px'))
</script>

<template>
  <t-layout class="layout-container">
    <t-aside :width="asideWidth" class="layout-aside border-r border-r-solid border-r-gray-200">
      <LayoutSide />
    </t-aside>
    <t-layout class="layout-main">
      <t-header class="layout-header">
        <LayoutHeader />
      </t-header>
      <t-content class="layout-content">
        <LayoutContent />
      </t-content>
    </t-layout>
  </t-layout>
</template>

<style scoped>
  .layout-container {
    width: 100vw;
    height: 100vh;
    max-width: 100%;
    overflow: hidden;
  }

  .layout-aside {
    height: 100vh;
    overflow-y: auto;
    overflow-x: hidden;
    flex-shrink: 0;
  }

  .layout-main {
    flex: 1;
    min-width: 0; /* 防止flex项目溢出 */
    height: 100vh;
    overflow: hidden;
    display: flex;
    flex-direction: column;
  }

  .layout-header {
    flex-shrink: 0;
  }

  .layout-content {
    flex: 1;
    min-height: 0; /* 重要：允许content区域滚动 */
    overflow: hidden;
  }
</style>
