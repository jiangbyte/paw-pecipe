<script lang="ts" setup>
  import { useUserProfileApi } from '@/api'
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
      const { data, success } = await withLoading(useUserProfileApi().GetUserProfile(row?.id))
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
        <t-descriptions-item label="真实姓名">
          {{ formData.realName }}
        </t-descriptions-item>
        <t-descriptions-item label="学校">
          {{ formData.school }}
        </t-descriptions-item>
        <t-descriptions-item label="专业">
          {{ formData.major }}
        </t-descriptions-item>
        <t-descriptions-item label="学号">
          {{ formData.studentId }}
        </t-descriptions-item>
        <t-descriptions-item label="公司">
          {{ formData.company }}
        </t-descriptions-item>
        <t-descriptions-item label="职位">
          {{ formData.jobTitle }}
        </t-descriptions-item>
        <t-descriptions-item label="行业">
          {{ formData.industry }}
        </t-descriptions-item>
        <t-descriptions-item label="国家">
          {{ formData.country }}
        </t-descriptions-item>
        <t-descriptions-item label="省份">
          {{ formData.province }}
        </t-descriptions-item>
        <t-descriptions-item label="城市">
          {{ formData.city }}
        </t-descriptions-item>
        <t-descriptions-item label="详细地址">
          {{ formData.location }}
        </t-descriptions-item>
        <t-descriptions-item label="QQ">
          {{ formData.qq }}
        </t-descriptions-item>
        <t-descriptions-item label="微信">
          {{ formData.wechat }}
        </t-descriptions-item>
        <t-descriptions-item label="是否显示生日">
          {{ formData.showBirthday }}
        </t-descriptions-item>
        <t-descriptions-item label="是否显示地理位置">
          {{ formData.showLocation }}
        </t-descriptions-item>
      </t-descriptions>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
