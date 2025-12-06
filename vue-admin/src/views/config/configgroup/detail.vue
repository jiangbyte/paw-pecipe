<script lang="ts" setup>
  import { useConfigGroupApi } from '@/api'
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
      const { data, success } = await withLoading(useConfigGroupApi().GetConfigGroup(row?.id))
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
        <t-descriptions-item label="分组名称">
          {{ formData.name }}
        </t-descriptions-item>
        <t-descriptions-item label="分组代码">
          {{ formData.code }}
        </t-descriptions-item>
        <t-descriptions-item label="分组描述">
          {{ formData.description }}
        </t-descriptions-item>
        <t-descriptions-item label="排序">
          {{ formData.sort }}
        </t-descriptions-item>
        <t-descriptions-item label="是否系统分组">
          {{ formData.isSystem }}
        </t-descriptions-item>
      </t-descriptions>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
