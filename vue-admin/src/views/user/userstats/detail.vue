<script lang="ts" setup>
  import { useUserStatsApi } from '@/api'
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
      const { data, success } = await withLoading(useUserStatsApi().GetUserStats(row?.id))
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
        <t-descriptions-item label="等级">
          {{ formData.level }}
        </t-descriptions-item>
        <t-descriptions-item label="经验值">
          {{ formData.exp }}
        </t-descriptions-item>
        <t-descriptions-item label="累计经验值">
          {{ formData.totalExp }}
        </t-descriptions-item>
        <t-descriptions-item label="登录天数">
          {{ formData.loginDays }}
        </t-descriptions-item>
        <t-descriptions-item label="连续登录天数">
          {{ formData.continuousLoginDays }}
        </t-descriptions-item>
        <t-descriptions-item label="发帖数">
          {{ formData.postCount }}
        </t-descriptions-item>
        <t-descriptions-item label="评论数">
          {{ formData.commentCount }}
        </t-descriptions-item>
        <t-descriptions-item label="获赞数">
          {{ formData.likeCount }}
        </t-descriptions-item>
        <t-descriptions-item label="关注数">
          {{ formData.followCount }}
        </t-descriptions-item>
        <t-descriptions-item label="粉丝数">
          {{ formData.fansCount }}
        </t-descriptions-item>
      </t-descriptions>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
