<script lang="ts" setup>
  import { useAuthGroupApi } from '@/api'
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
      const { data, success } = await withLoading(useAuthGroupApi().GetAuthGroup(row?.id))
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
        <t-descriptions-item label="父级组ID">
          {{ formData.parentId }}
        </t-descriptions-item>
        <t-descriptions-item label="用户组名称">
          {{ formData.name }}
        </t-descriptions-item>
        <t-descriptions-item label="用户组编码">
          {{ formData.code }}
        </t-descriptions-item>
        <t-descriptions-item label="用户组描述">
          {{ formData.description }}
        </t-descriptions-item>
        <t-descriptions-item label="排序号">
          {{ formData.sort }}
        </t-descriptions-item>
        <t-descriptions-item label="管理员ID">
          {{ formData.adminId }}
        </t-descriptions-item>
        <t-descriptions-item label="最大用户数量限制">
          {{ formData.maxUserCount }}
        </t-descriptions-item>
      </t-descriptions>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
