<script lang="ts" setup>
  import { useConfigItemApi } from '@/api'
  import { useBoolean, useLoading } from '@/hooks'
  import { ResetFormData } from '@/utils'

  const props = defineProps<{
    formName?: string
  }>()

  const { value: visible, setFalse: closeDrawer, setTrue: openDrawer } = useBoolean(false)
  const { isLoading, withLoading } = useLoading()

  const formData = reactive<DataFormType>({})

  function doClose() {
    ResetFormData(formData)
    closeDrawer()
  }

  async function doOpen(row: any) {
    openDrawer()
    ResetFormData(formData)

    if (row?.id) {
      const { data, success } = await withLoading(useConfigItemApi().GetConfigItem(row?.id))
      if (success) {
        Object.assign(formData, data)
      } else {
        closeDrawer()
      }
    }
  }

  defineExpose({
    doOpen
  })
</script>

<template>
  <t-drawer
    v-model:visible="visible"
    :close-btn="true"
    :confirm-btn="null"
    size="large"
    destroy-on-close
    @close="doClose"
  >
    <template #header>
      {{ `${props.formName}详情` }}
    </template>
    <t-loading size="small" :loading="isLoading" show-overlay class="w-full">
      <t-descriptions :column="1" colon table-layout="auto">
        <t-descriptions-item label="分组编码">
          {{ formData.groupCode }}
        </t-descriptions-item>
        <t-descriptions-item label="配置项名称">
          {{ formData.name }}
        </t-descriptions-item>
        <t-descriptions-item label="配置项代码">
          {{ formData.code }}
        </t-descriptions-item>
        <t-descriptions-item label="配置值">
          {{ formData.value }}
        </t-descriptions-item>
        <t-descriptions-item label="组件类型">
          {{ formData.componentType }}
        </t-descriptions-item>
        <t-descriptions-item label="配置描述">
          {{ formData.description }}
        </t-descriptions-item>
        <t-descriptions-item label="排序">
          {{ formData.sort }}
        </t-descriptions-item>
      </t-descriptions>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
