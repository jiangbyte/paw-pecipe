<script lang="ts" setup>
  import { useUserProfileApi } from '@/api'
  import { useBoolean, useLoading } from '@/hooks'
  import { ResetFormData } from '@/utils'

  const props = defineProps<{
    formName?: string
  }>()

  const emit = defineEmits(['close', 'submit'])

  const { value: visible, setFalse: closeDrawer, setTrue: openDrawer } = useBoolean(false)
  const { value: isEdit, setFalse: setAddMode, setTrue: setEditMode } = useBoolean(false)
  const { isLoading, withLoading } = useLoading()

  const formData = reactive<DataFormType>({})

  async function doOpen(row: any) {
    openDrawer()
    ResetFormData(formData)

    if (row?.id) {
      setEditMode()
      const { data, success } = await withLoading(useUserProfileApi().GetUserProfile(row?.id))
      if (success) {
        Object.assign(formData, data)
      } else {
        closeDrawer()
      }
    } else {
      setAddMode()
    }
  }

  function doClose() {
    ResetFormData(formData)
    closeDrawer()
    emit('close')
  }

  async function doSubmit() {
    const api = isEdit.value
      ? useUserProfileApi().EditUserProfile
      : useUserProfileApi().AddUserProfile

    const { success } = await withLoading(api(formData))
    if (success) {
      closeDrawer()
      emit('submit')
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
    :close-on-overlay-click="false"
    :confirm-btn="{ disabled: isLoading }"
    size="large"
    destroy-on-close
    @close="doClose"
    @confirm="doSubmit"
  >
    <template #header>
      {{ isEdit ? `编辑${props.formName}` : `新增${props.formName}` }}
    </template>
    <t-loading size="small" :loading="isLoading" show-overlay class="w-full">
      <t-form :data="formData" label-align="left">
        <t-form-item label="账户ID" name="accountId">
          <t-input v-model="formData.accountId" placeholder="请输入账户ID" />
        </t-form-item>
        <t-form-item label="真实姓名" name="realName">
          <t-input v-model="formData.realName" placeholder="请输入真实姓名" />
        </t-form-item>
        <t-form-item label="学校" name="school">
          <t-input v-model="formData.school" placeholder="请输入学校" />
        </t-form-item>
        <t-form-item label="专业" name="major">
          <t-input v-model="formData.major" placeholder="请输入专业" />
        </t-form-item>
        <t-form-item label="学号" name="studentId">
          <t-input v-model="formData.studentId" placeholder="请输入学号" />
        </t-form-item>
        <t-form-item label="公司" name="company">
          <t-input v-model="formData.company" placeholder="请输入公司" />
        </t-form-item>
        <t-form-item label="职位" name="jobTitle">
          <t-input v-model="formData.jobTitle" placeholder="请输入职位" />
        </t-form-item>
        <t-form-item label="行业" name="industry">
          <t-input v-model="formData.industry" placeholder="请输入行业" />
        </t-form-item>
        <t-form-item label="国家" name="country">
          <t-input v-model="formData.country" placeholder="请输入国家" />
        </t-form-item>
        <t-form-item label="省份" name="province">
          <t-input v-model="formData.province" placeholder="请输入省份" />
        </t-form-item>
        <t-form-item label="城市" name="city">
          <t-input v-model="formData.city" placeholder="请输入城市" />
        </t-form-item>
        <t-form-item label="详细地址" name="location">
          <t-input v-model="formData.location" placeholder="请输入详细地址" />
        </t-form-item>
        <t-form-item label="QQ" name="qq">
          <t-input v-model="formData.qq" placeholder="请输入QQ" />
        </t-form-item>
        <t-form-item label="微信" name="wechat">
          <t-input v-model="formData.wechat" placeholder="请输入微信" />
        </t-form-item>
        <t-form-item label="是否显示生日" name="showBirthday">
          <t-input v-model="formData.showBirthday" placeholder="请输入是否显示生日" />
        </t-form-item>
        <t-form-item label="是否显示地理位置" name="showLocation">
          <t-input v-model="formData.showLocation" placeholder="请输入是否显示地理位置" />
        </t-form-item>
      </t-form>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
