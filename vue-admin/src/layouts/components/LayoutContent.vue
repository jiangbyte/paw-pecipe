<script lang="ts" setup></script>

<template>
  <div style="min-width: 800px" class="content-wrapper content-container">
    <router-view v-slot="{ Component, route }">
      <keep-alive>
        <component :is="Component" :key="route.fullPath" />
      </keep-alive>
    </router-view>
  </div>
</template>

<style scoped>
  .content-wrapper {
    height: 100%;
    overflow: hidden;
    display: flex;
    flex-direction: column;
    background-color: white;
  }

  .content-container {
    flex: 1;
    min-height: 0; /* 重要：允许内部滚动 */
    overflow: auto;
    box-sizing: border-box;
  }
</style>
