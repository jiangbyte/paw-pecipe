<script lang="ts" setup>
  import { useAccessApi } from '@/api'
  import { useAppStore } from '@/stores'
  import { PasswordUtil, VerifyCapchaRule, VerifyPasswordRule } from '@/utils'

  const formRef = ref()
  const formData = ref({
    token: '',
    newPassword: '',
    confirmPassword: '',
    captchaId: '',
    captchaCode: ''
  })
  const formRules = {
    newPassword: VerifyPasswordRule,
    confirmPassword: VerifyPasswordRule,
    captchaCode: VerifyCapchaRule
  } as any

  const captchaRef = ref({
    captchaId: '',
    captchaImg: ''
  })
  async function loadCaptcha() {
    captchaRef.value.captchaId = ''
    captchaRef.value.captchaImg = ''
    formData.value.captchaId = ''
    formData.value.captchaCode = ''
    useAccessApi()
      .Captcha()
      .then(({ data }) => {
        captchaRef.value = data
        formData.value.captchaId = captchaRef.value.captchaId
      })
  }

  loadCaptcha()

  const router = useRouter()
  const isLoading = ref(false)

  const route = useRoute()
  // 从URL参数获取token
  onMounted(() => {
    const tokenParam = route.query.token as string
    if (tokenParam) {
      formData.value.token = tokenParam

      useAccessApi()
        .DoValidateResetPasswordToken(formData.value.token)
        .then(({ success }) => {
          if (!success) {
            MessagePlugin.error('无效的重置链接')
            router.push('/login')
          }
        })
    } else {
      MessagePlugin.error('无效的重置链接')
      router.push('/login')
    }
  })
  async function handleSubmit(context: any) {
    const { validateResult } = context
    if (validateResult === true) {
      isLoading.value = true
      const formDataParam = Object.assign({}, formData.value)
      formDataParam.newPassword = PasswordUtil.encrypt(formData.value.newPassword)
      formDataParam.confirmPassword = PasswordUtil.encrypt(formData.value.confirmPassword)
      useAccessApi()
        .DoPasswordResetConfirm(formDataParam)
        .then(({ success }) => {
          isLoading.value = false
          if (success) {
            MessagePlugin.info('密码重置成功！')
            router.push('/')
          } else {
            loadCaptcha()
            MessagePlugin.error('密码重置失败，请重试')
          }
        })
    }
  }

  const appStore = useAppStore()
  const { websiteConfig } = storeToRefs(appStore)
</script>

<template>
  <div class="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 flex flex-col md:flex-row">
    <div class="w-full md:w-1/2 flex items-center justify-center p-8">
      <div class="w-full max-w-md">
        <div class="text-center mb-10">
          <h2 class="text-2xl font-bold text-gray-800 mb-2">重置密码</h2>
          <p class="text-gray-500">欢迎回来，请登录您的账号</p>
        </div>

        <t-form ref="formRef" :data="formData" :rules="formRules" @submit="handleSubmit">
          <t-form-item label="密码" name="newPassword">
            <t-input
              v-model="formData.newPassword"
              type="password"
              placeholder="请输入密码"
              class="w-full"
              :visibility-toggle="true"
              size="large"
            >
              <template #prefix-icon>
                <t-icon name="lock-on" />
              </template>
            </t-input>
          </t-form-item>
          <t-form-item label="确认密码" name="confirmPassword">
            <t-input
              v-model="formData.confirmPassword"
              type="password"
              placeholder="请再次输入密码"
              class="w-full"
              :visibility-toggle="true"
              size="large"
            >
              <template #prefix-icon>
                <t-icon name="lock-on" />
              </template>
            </t-input>
          </t-form-item>
          <t-form-item label="验证码" name="captchaCode">
            <div class="flex items-center justify-between w-full">
              <t-input
                v-model="formData.captchaCode"
                placeholder="请输入验证码"
                size="large"
                style="width: 215px"
              >
                <template #prefix-icon>
                  <t-icon name="numbers-5-1" />
                </template>
              </t-input>
              <img
                :src="captchaRef.captchaImg"
                class="w-30 h-full ml-2 cursor-pointer border border-gray-200 object-cover"
                @click="loadCaptcha"
              />
            </div>
          </t-form-item>
          <t-form-item label-width="0">
            <t-button theme="primary" block type="submit" :loading="isLoading" size="large">
              {{ isLoading ? '重置中...' : '重置密码' }}
            </t-button>
          </t-form-item>
          <t-form-item label-width="0">
            <div class="flex flex-col w-full">
              <div class="text-center">
                <span class="text-gray-500">想起密码了?</span>
                <t-link theme="primary" @click="$router.push('/login')">立即登录</t-link>
              </div>
            </div>
          </t-form-item>
        </t-form>
      </div>
    </div>

    <div class="w-full md:w-1/2 bg-blue-900 text-white p-8 md:p-16 flex flex-col justify-between">
      <div>
        <div class="flex items-center mb-12">
          <div class="w-12 h-12 rounded-full bg-white flex items-center justify-center mr-4">
            <span class="text-blue-900 font-bold text-xl">
              {{ websiteConfig?.websiteName?.charAt(0) }}
            </span>
          </div>
          <h1 class="text-3xl font-bold">
            {{ websiteConfig?.websiteName }}
          </h1>
        </div>
        <div class="mt-12">
          <p class="text-blue-100 mt-4 leading-relaxed">
            {{ websiteConfig?.websiteDescription }}
          </p>
        </div>
      </div>
      <div class="mt-16">
        <p class="text-blue-200 text-sm">
          {{ websiteConfig?.websiteCopyright }}
        </p>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
