<script lang="ts" setup>
  import { useAuthAccountApi, useSysDictApi } from '@/api'
  import { useBoolean, useLoading } from '@/hooks'
  import { ResetFormData } from '@/utils'

  const props = defineProps<{
    formName?: string
  }>()

  const emit = defineEmits(['close', 'submit'])

  const { value: visible, setFalse: closeDrawer, setTrue: openDrawer } = useBoolean(false)
  const { value: isEdit, setFalse: setAddMode, setTrue: setEditMode } = useBoolean(false)
  const { isLoading, withLoading } = useLoading()
  const {
    isLoading: accountStatusTypeOptionsLoading,
    withLoading: withAccountStatusTypeOptionsLoading
  } = useLoading()

  const tabsValue = ref('account')
  const formData = reactive<DataFormType>({})
  const accountStatusTypeOptions = ref<TypeOption[]>([])
  async function doOpen(row: any) {
    openDrawer()
    ResetFormData(formData)

    if (row?.id) {
      setEditMode()
      const { data, success } = await withLoading(useAuthAccountApi().GetAuthAccount(row?.id))
      if (success) {
        Object.assign(formData, data)
      } else {
        closeDrawer()
      }
    } else {
      setAddMode()
    }

    withAccountStatusTypeOptionsLoading(
      useSysDictApi().ListOptionsByType('SYS_ACCOUNT_STATUS')
    ).then(({ data }) => {
      accountStatusTypeOptions.value = data.map((item: TypeOption) => ({
        ...item,
        value: Number(item.value)
      }))
    })
  }

  function doClose() {
    ResetFormData(formData)
    closeDrawer()
    emit('close')
  }

  async function doSubmit() {
    const api = isEdit.value
      ? useAuthAccountApi().EditAuthAccount
      : useAuthAccountApi().AddAuthAccount

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
      <t-tabs v-model="tabsValue">
        <t-tab-panel value="account" label="账户信息" :destroy-on-hide="true">
          <t-form :data="formData" label-align="left">
            <t-form-item label="用户名" name="username" class="mt-4">
              <t-input v-model="formData.username" placeholder="请输入用户名" />
            </t-form-item>
            <t-form-item label="邮箱地址" name="email">
              <t-input v-model="formData.email" placeholder="请输入邮箱地址" />
            </t-form-item>
            <t-form-item label="手机号码" name="telephone">
              <t-input v-model="formData.telephone" placeholder="请输入手机号码" />
            </t-form-item>
            <t-form-item label="账户状态" name="status">
              <t-select
                v-model="formData.status"
                :options="accountStatusTypeOptions"
                :keys="{ label: 'text' }"
              />
            </t-form-item>
          </t-form>
        </t-tab-panel>
        <t-tab-panel value="info" label="基础信息" :destroy-on-hide="true">
          <t-form :data="formData" label-align="left">
            <t-form-item label="昵称" name="nickname" class="mt-4">
              <t-input v-model="formData.nickname" placeholder="请输入昵称" />
            </t-form-item>
            <t-form-item label="头像" name="avatar">
              <t-input v-model="formData.avatar" placeholder="请输入头像" />
            </t-form-item>
            <t-form-item label="性别" name="gender">
              <t-input v-model="formData.gender" placeholder="请输入性别" />
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
        </t-tab-panel>
        <t-tab-panel value="profile" label="用户档案" :destroy-on-hide="true">
          <t-form :data="formData" label-align="left">
            <t-form-item label="真实姓名" name="realName" class="mt-4">
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
          </t-form>
        </t-tab-panel>
      </t-tabs>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
