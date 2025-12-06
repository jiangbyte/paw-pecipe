<script lang="ts" setup>
  import { useUserInfoApi } from '@/api'
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
      const { data, success } = await withLoading(useUserInfoApi().GetUserInfo(row?.id))
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
        <t-descriptions-item label="昵称">
          {{ formData.nickname }}
        </t-descriptions-item>
        <t-descriptions-item label="头像">
          {{ formData.avatar }}
        </t-descriptions-item>
        <t-descriptions-item label="性别：0-未知 1-男 2-女">
          {{ formData.gender }}
        </t-descriptions-item>
        <t-descriptions-item label="生日">
          {{ formData.birthday }}
        </t-descriptions-item>
        <t-descriptions-item label="个性签名">
          {{ formData.signature }}
        </t-descriptions-item>
        <t-descriptions-item label="个人背景图片">
          {{ formData.background }}
        </t-descriptions-item>
        <t-descriptions-item label="兴趣标签">
          {{ formData.interests }}
        </t-descriptions-item>
        <t-descriptions-item label="个人网站">
          {{ formData.website }}
        </t-descriptions-item>
        <t-descriptions-item label="GitHub">
          {{ formData.github }}
        </t-descriptions-item>
        <t-descriptions-item label="GitTee">
          {{ formData.gitee }}
        </t-descriptions-item>
        <t-descriptions-item label="博客">
          {{ formData.blog }}
        </t-descriptions-item>
      </t-descriptions>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
