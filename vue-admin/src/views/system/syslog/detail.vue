<script lang="ts" setup>
  import { useSysLogApi } from '@/api'
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
      const { data, success } = await withLoading(useSysLogApi().GetSysLog(row?.id))
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
        <t-descriptions-item label="用户ID">
          {{ formData.userId }}
        </t-descriptions-item>
        <t-descriptions-item label="操作类型">
          {{ formData.operation }}
        </t-descriptions-item>
        <t-descriptions-item label="请求方法">
          {{ formData.method }}
        </t-descriptions-item>
        <t-descriptions-item label="请求参数">
          {{ formData.params }}
        </t-descriptions-item>
        <t-descriptions-item label="IP地址">
          {{ formData.ip }}
        </t-descriptions-item>
        <t-descriptions-item label="操作时间">
          {{ formData.operationTime }}
        </t-descriptions-item>
        <t-descriptions-item label="日志分类">
          {{ formData.category }}
        </t-descriptions-item>
        <t-descriptions-item label="操作模块">
          {{ formData.module }}
        </t-descriptions-item>
        <t-descriptions-item label="操作描述">
          {{ formData.description }}
        </t-descriptions-item>
        <t-descriptions-item label="操作状态">
          {{ formData.status }}
        </t-descriptions-item>
        <t-descriptions-item label="日志消息">
          {{ formData.message }}
        </t-descriptions-item>
      </t-descriptions>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
