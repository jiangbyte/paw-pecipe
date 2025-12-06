<script lang="ts" setup>
  import { useAccessApi } from '@/api'
  import { useAppStore } from '@/stores'
  import { VerifyCapchaRule, VerifyEmailRule } from '@/utils'

  const formRef = ref()
  const formData = ref({
    email: '',
    captchaId: '',
    captchaCode: ''
  })
  const formRules = {
    email: VerifyEmailRule,
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

  const isLoading = ref(false)
  const router = useRouter()
  async function handleSubmit(context: any) {
    const { validateResult } = context
    if (validateResult === true) {
      isLoading.value = true
      useAccessApi()
        .DoResetPassword(formData.value)
        .then(({ success }) => {
          isLoading.value = false
          if (success) {
            MessagePlugin.info('重置邮件发送成功！')
            router.push('/login')
          } else {
            loadCaptcha()
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
          <h2 class="text-2xl font-bold text-gray-800 mb-2">忘记密码</h2>
          <p class="text-gray-500">输入您的注册邮箱，我们将发送重置密码的指引邮件给您</p>
        </div>

        <t-form ref="formRef" :data="formData" :rules="formRules" @submit="handleSubmit">
          <t-form-item label="注册邮箱" name="email">
            <t-input
              v-model="formData.email"
              placeholder="请输入注册邮箱"
              class="w-full"
              size="large"
            >
              <template #prefix-icon>
                <t-icon name="mail" />
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
              {{ isLoading ? '发送中...' : '发送重置邮件' }}
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
