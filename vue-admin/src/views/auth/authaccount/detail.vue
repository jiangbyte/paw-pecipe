<script lang="ts" setup>
  import { useAuthAccountApi } from '@/api'
  import { useBoolean, useLoading } from '@/hooks'
  import { ResetFormData } from '@/utils'

  const props = defineProps<{
    formName?: string
  }>()

  const { value: visible, setFalse: closeDrawer, setTrue: openDrawer } = useBoolean(false)
  const { isLoading, withLoading } = useLoading()

  const tabsValue = ref('account')
  const formData = reactive<DataFormType>({})

  function doClose() {
    ResetFormData(formData)
    closeDrawer()
  }

  async function doOpen(row: any) {
    openDrawer()
    ResetFormData(formData)

    if (row?.id) {
      const { data, success } = await withLoading(useAuthAccountApi().GetUserAccount(row?.id))
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
      <t-tabs v-model="tabsValue">
        <t-tab-panel value="account" label="账户信息" :destroy-on-hide="true">
          <t-descriptions :column="1" colon table-layout="auto">
            <t-descriptions-item label="用户名">
              <t-typography-text copyable>
                {{ formData.account?.username }}
              </t-typography-text>
            </t-descriptions-item>
            <t-descriptions-item label="邮箱地址">
              <t-typography-text copyable>
                {{ formData.account?.email }}
              </t-typography-text>
            </t-descriptions-item>
            <t-descriptions-item label="手机号码">
              <t-typography-text v-if="formData.account?.telephone" copyable>
                {{ formData.account?.telephone }}
              </t-typography-text>
            </t-descriptions-item>
            <t-descriptions-item label="账户状态">
              {{ formData.account?.statusName }}
            </t-descriptions-item>
            <t-descriptions-item label="密码强度等级">
              <t-rate :value="formData.account?.passwordStrength" :disabled="true" :count="3" />
            </t-descriptions-item>
            <t-descriptions-item label="最后修改密码的时间">
              {{ formData.account?.lastPasswordChange }}
            </t-descriptions-item>
            <t-descriptions-item label="最后登录时间">
              {{ formData.account?.lastLoginTime }}
            </t-descriptions-item>
            <t-descriptions-item label="最后登录IP地址">
              <t-typography-text v-if="formData.account?.lastLoginIp" copyable>
                {{ formData.account?.lastLoginIp }}
              </t-typography-text>
            </t-descriptions-item>
            <t-descriptions-item label="登录次数统计">
              {{ formData.account?.loginCount }}
            </t-descriptions-item>
          </t-descriptions>
        </t-tab-panel>
        <t-tab-panel value="info" label="基础信息" :destroy-on-hide="true">
          <t-descriptions :column="1" colon table-layout="auto">
            <t-descriptions-item label="昵称">
              {{ formData.info?.nickname }}
            </t-descriptions-item>
            <t-descriptions-item label="头像">
              <t-avatar :image="formData.info?.avatar" />
            </t-descriptions-item>
            <t-descriptions-item label="性别">
              {{ formData.info?.genderName }}
            </t-descriptions-item>
            <t-descriptions-item label="生日">
              {{ formData.info?.birthday }}
            </t-descriptions-item>
            <t-descriptions-item label="个性签名">
              {{ formData.info?.signature }}
            </t-descriptions-item>
            <t-descriptions-item label="个人背景图片">
              {{ formData.info?.background }}
            </t-descriptions-item>
            <t-descriptions-item label="兴趣标签">
              {{ formData.info?.interests }}
            </t-descriptions-item>
            <t-descriptions-item label="个人网站">
              {{ formData.info?.website }}
            </t-descriptions-item>
            <t-descriptions-item label="GitHub">
              {{ formData.info?.github }}
            </t-descriptions-item>
            <t-descriptions-item label="GitTee">
              {{ formData.info?.gitee }}
            </t-descriptions-item>
            <t-descriptions-item label="博客">
              {{ formData.info?.blog }}
            </t-descriptions-item>
          </t-descriptions>
        </t-tab-panel>
        <t-tab-panel value="profile" label="用户档案" :destroy-on-hide="true">
          <t-descriptions :column="1" colon table-layout="auto">
            <t-descriptions-item label="真实姓名">
              {{ formData.profile?.realName }}
            </t-descriptions-item>
            <t-descriptions-item label="学校">
              {{ formData.profile?.school }}
            </t-descriptions-item>
            <t-descriptions-item label="专业">
              {{ formData.profile?.major }}
            </t-descriptions-item>
            <t-descriptions-item label="学号">
              {{ formData.profile?.studentId }}
            </t-descriptions-item>
            <t-descriptions-item label="公司">
              {{ formData.profile?.company }}
            </t-descriptions-item>
            <t-descriptions-item label="职位">
              {{ formData.profile?.jobTitle }}
            </t-descriptions-item>
            <t-descriptions-item label="行业">
              {{ formData.profile?.industry }}
            </t-descriptions-item>
            <t-descriptions-item label="国家">
              {{ formData.profile?.country }}
            </t-descriptions-item>
            <t-descriptions-item label="省份">
              {{ formData.profile?.province }}
            </t-descriptions-item>
            <t-descriptions-item label="城市">
              {{ formData.profile?.city }}
            </t-descriptions-item>
            <t-descriptions-item label="详细地址">
              {{ formData.profile?.location }}
            </t-descriptions-item>
            <t-descriptions-item label="QQ">
              {{ formData.profile?.qq }}
            </t-descriptions-item>
            <t-descriptions-item label="微信">
              {{ formData.profile?.wechat }}
            </t-descriptions-item>
            <t-descriptions-item label="是否显示生日">
              {{ formData.profile?.showBirthday }}
            </t-descriptions-item>
            <t-descriptions-item label="是否显示地理位置">
              {{ formData.profile?.showLocation }}
            </t-descriptions-item>
          </t-descriptions>
        </t-tab-panel>
        <t-tab-panel value="preference" label="偏好设置" :destroy-on-hide="true">
          <t-descriptions :column="1" colon table-layout="auto">
            <t-descriptions-item label="主题">
              {{ formData.preference?.theme }}
            </t-descriptions-item>
            <t-descriptions-item label="系统语言">
              {{ formData.preference?.language }}
            </t-descriptions-item>
            <t-descriptions-item label="邮件通知">
              {{ formData.preference?.emailNotifications }}
            </t-descriptions-item>
            <t-descriptions-item label="推送通知">
              {{ formData.preference?.pushNotifications }}
            </t-descriptions-item>
            <t-descriptions-item label="允许私信">
              {{ formData.preference?.allowDirectMessage }}
            </t-descriptions-item>
          </t-descriptions>
        </t-tab-panel>
        <t-tab-panel value="stats" label="数据统计" :destroy-on-hide="true">
          <t-descriptions :column="1" colon table-layout="auto">
            <t-descriptions-item label="等级">
              {{ formData.stats?.level }}
            </t-descriptions-item>
            <t-descriptions-item label="经验值">
              {{ formData.stats?.exp }}
            </t-descriptions-item>
            <t-descriptions-item label="累计经验值">
              {{ formData.stats?.totalExp }}
            </t-descriptions-item>
            <t-descriptions-item label="登录天数">
              {{ formData.stats?.loginDays }}
            </t-descriptions-item>
            <t-descriptions-item label="连续登录天数">
              {{ formData.stats?.continuousLoginDays }}
            </t-descriptions-item>
            <t-descriptions-item label="发帖数">
              {{ formData.stats?.postCount }}
            </t-descriptions-item>
            <t-descriptions-item label="评论数">
              {{ formData.stats?.commentCount }}
            </t-descriptions-item>
            <t-descriptions-item label="获赞数">
              {{ formData.stats?.likeCount }}
            </t-descriptions-item>
            <t-descriptions-item label="关注数">
              {{ formData.stats?.followCount }}
            </t-descriptions-item>
            <t-descriptions-item label="粉丝数">
              {{ formData.stats?.fansCount }}
            </t-descriptions-item>
          </t-descriptions>
        </t-tab-panel>
      </t-tabs>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
