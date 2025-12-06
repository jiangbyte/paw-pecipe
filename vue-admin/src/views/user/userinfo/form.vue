<script lang="ts" setup>
  import { useUserInfoApi } from '@/api'
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
      const { data, success } = await withLoading(useUserInfoApi().GetUserInfo(row?.id))
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
    const api = isEdit.value ? useUserInfoApi().EditUserInfo : useUserInfoApi().AddUserInfo

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
        <t-form-item label="昵称" name="nickname">
          <t-input v-model="formData.nickname" placeholder="请输入昵称" />
        </t-form-item>
        <t-form-item label="头像" name="avatar">
          <t-input v-model="formData.avatar" placeholder="请输入头像" />
        </t-form-item>
        <t-form-item label="性别：0-未知 1-男 2-女" name="gender">
          <t-input v-model="formData.gender" placeholder="请输入性别：0-未知 1-男 2-女" />
        </t-form-item>
        <t-form-item label="生日" name="birthday">
          <t-input v-model="formData.birthday" placeholder="请输入生日" />
        </t-form-item>
        <t-form-item label="个性签名" name="signature">
          <t-input v-model="formData.signature" placeholder="请输入个性签名" />
        </t-form-item>
        <t-form-item label="个人背景图片" name="background">
          <t-input v-model="formData.background" placeholder="请输入个人背景图片" />
        </t-form-item>
        <t-form-item label="兴趣标签" name="interests">
          <t-input v-model="formData.interests" placeholder="请输入兴趣标签" />
        </t-form-item>
        <t-form-item label="个人网站" name="website">
          <t-input v-model="formData.website" placeholder="请输入个人网站" />
        </t-form-item>
        <t-form-item label="GitHub" name="github">
          <t-input v-model="formData.github" placeholder="请输入GitHub" />
        </t-form-item>
        <t-form-item label="GitTee" name="gitee">
          <t-input v-model="formData.gitee" placeholder="请输入GitTee" />
        </t-form-item>
        <t-form-item label="博客" name="blog">
          <t-input v-model="formData.blog" placeholder="请输入博客" />
        </t-form-item>
      </t-form>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
