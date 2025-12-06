<script lang="ts" setup>
  import { useUserPreferenceApi } from '@/api'
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
      const { data, success } = await withLoading(useUserPreferenceApi().GetUserPreference(row?.id))
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
        <t-descriptions-item label="账户ID">
          {{ formData.accountId }}
        </t-descriptions-item>
        <t-descriptions-item label="主题">
          {{ formData.theme }}
        </t-descriptions-item>
        <t-descriptions-item label="系统语言">
          {{ formData.language }}
        </t-descriptions-item>
        <t-descriptions-item label="邮件通知">
          {{ formData.emailNotifications }}
        </t-descriptions-item>
        <t-descriptions-item label="推送通知">
          {{ formData.pushNotifications }}
        </t-descriptions-item>
        <t-descriptions-item label="允许私信">
          {{ formData.allowDirectMessage }}
        </t-descriptions-item>
      </t-descriptions>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
