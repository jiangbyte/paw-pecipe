<script lang="ts" setup>
  import { useDashboardApi } from '@/api'
  import { useLoading } from '@/hooks'

  interface PaneItem {
    icon: string
    title: string
    value: number
    unit: string
  }

  const listData = ref<PaneItem[]>([])
  const { isLoading, withLoading } = useLoading()

  async function loadPageData() {
    const { data } = await withLoading(useDashboardApi().GetDashboardPaneItems())
    listData.value = data
  }
  loadPageData()
</script>

<template>
  <t-space direction="vertical" class="p-2">
    <t-row :gutter="[16, 16]">
      <t-col v-for="(item, index) in listData" :key="index" :span="3">
        <t-card :bordered="false">
          <t-space align="center">
            <t-icon :name="item.icon" class="icon" />
            <t-statistic
              :title="item.title"
              :value="item.value"
              :unit="item.unit"
              :animation-start="!isLoading"
              :animation="{
                valueFrom: 0,
                duration: 2000
              }"
            />
          </t-space>
        </t-card>
      </t-col>
    </t-row>
  </t-space>
</template>

<style scoped>
  .icon {
    font-size: 54px;
    color: var(--td-brand-color);
    background: var(--td-brand-color-light);
    border-radius: var(--td-radius-medium);
    padding: 12px;
  }
</style>
