<script lang="ts" setup>
  import { useUserStatsApi } from '@/api'
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
      const { data, success } = await withLoading(useUserStatsApi().GetUserStats(row?.id))
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
    const api = isEdit.value ? useUserStatsApi().EditUserStats : useUserStatsApi().AddUserStats

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
        <t-form-item label="等级" name="level">
          <t-input v-model="formData.level" placeholder="请输入等级" />
        </t-form-item>
        <t-form-item label="经验值" name="exp">
          <t-input v-model="formData.exp" placeholder="请输入经验值" />
        </t-form-item>
        <t-form-item label="累计经验值" name="totalExp">
          <t-input v-model="formData.totalExp" placeholder="请输入累计经验值" />
        </t-form-item>
        <t-form-item label="登录天数" name="loginDays">
          <t-input v-model="formData.loginDays" placeholder="请输入登录天数" />
        </t-form-item>
        <t-form-item label="连续登录天数" name="continuousLoginDays">
          <t-input v-model="formData.continuousLoginDays" placeholder="请输入连续登录天数" />
        </t-form-item>
        <t-form-item label="发帖数" name="postCount">
          <t-input v-model="formData.postCount" placeholder="请输入发帖数" />
        </t-form-item>
        <t-form-item label="评论数" name="commentCount">
          <t-input v-model="formData.commentCount" placeholder="请输入评论数" />
        </t-form-item>
        <t-form-item label="获赞数" name="likeCount">
          <t-input v-model="formData.likeCount" placeholder="请输入获赞数" />
        </t-form-item>
        <t-form-item label="关注数" name="followCount">
          <t-input v-model="formData.followCount" placeholder="请输入关注数" />
        </t-form-item>
        <t-form-item label="粉丝数" name="fansCount">
          <t-input v-model="formData.fansCount" placeholder="请输入粉丝数" />
        </t-form-item>
      </t-form>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
