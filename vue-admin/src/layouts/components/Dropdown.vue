<script lang="ts" setup>
  import { useAuthStore } from '@/stores'

  const authStore = useAuthStore()
  const { user } = storeToRefs(authStore)

  const visibleLogoutDialog = ref(false)
</script>

<template>
  <t-dropdown :min-column-width="120" trigger="click">
    <template #dropdown>
      <t-dropdown-item>
        <t-space :size="8">
          <t-icon name="user-circle" size="16" />
          个人中心
        </t-space>
      </t-dropdown-item>
      <t-dropdown-item theme="error" @click="visibleLogoutDialog = true">
        <t-space :size="8">
          <t-icon name="logout" size="16" />
          登出
        </t-space>
      </t-dropdown-item>
    </template>
    <t-space :size="8" align="center" class="ml-2">
      <t-avatar :image="user?.avatar" />
      {{ user?.nickname }}
    </t-space>
  </t-dropdown>
  <t-dialog
    v-model:visible="visibleLogoutDialog"
    theme="danger"
    header="登出操作"
    @confirm="authStore.logoutAndRedirect"
  >
    <template #body>
      <div class="flex w-full items-center justify-start">确认登出吗？</div>
    </template>
  </t-dialog>
</template>

<style scoped></style>
