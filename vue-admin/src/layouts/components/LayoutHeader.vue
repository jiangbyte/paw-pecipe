<script lang="ts" setup>
  import { useAppStore } from '@/stores'
  import Dropdown from './Dropdown.vue'

  const appStore = useAppStore()

  const route = useRoute()
  const routes = computed(() => {
    return route.matched
  })
</script>

<template>
  <t-head-menu style="min-width: 800px" class="border-b border-b-solid border-b-gray-200">
    <template #logo>
      <t-space align="center">
        <t-button shape="square" variant="text" @click="appStore.toggleCollapse">
          <t-icon v-if="appStore.collapsed" name="chevron-right" />
          <t-icon v-else name="chevron-left" />
        </t-button>
        <t-breadcrumb max-item-width="150">
          <t-breadcrumb-item v-for="item in routes" :key="item.path" :to="item.path">
            <template #icon>
              <t-icon :name="item.meta.icon" />
            </template>
            {{ item.meta.title }}
          </t-breadcrumb-item>
        </t-breadcrumb>
        <t-input placeholder="请输入搜索内容" clearable>
          <template #suffixIcon>
            <t-icon name="search" />
          </template>
        </t-input>
      </t-space>
    </template>
    <template #operations>
      <t-space align="center" :size="2" class="mr-4">
        <t-button variant="text" shape="square">
          <t-badge count="2" dot>
            <t-icon name="notification" />
          </t-badge>
        </t-button>
        <t-button variant="text" shape="square">
          <template #icon>
            <t-icon name="logo-github" />
          </template>
        </t-button>
        <t-button variant="text" shape="square">
          <template #icon>
            <t-icon name="setting" />
          </template>
        </t-button>
        <Dropdown />
      </t-space>
    </template>
  </t-head-menu>
</template>

<style scoped></style>
