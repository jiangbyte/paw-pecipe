<script lang="ts" setup>
  import { useSysMenuApi } from '@/api'
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
      const { data, success } = await withLoading(useSysMenuApi().GetSysMenu(row?.id))
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
        <t-descriptions-item label="父级ID">
          {{ formData.pid }}
        </t-descriptions-item>
        <t-descriptions-item label="菜单名称">
          {{ formData.name }}
        </t-descriptions-item>
        <t-descriptions-item label="菜单路径">
          {{ formData.path }}
        </t-descriptions-item>
        <t-descriptions-item label="组件路径">
          {{ formData.componentPath }}
        </t-descriptions-item>
        <t-descriptions-item label="重定向路径">
          {{ formData.redirect }}
        </t-descriptions-item>
        <t-descriptions-item label="外部链接地址">
          {{ formData.externalUrl }}
        </t-descriptions-item>
        <t-descriptions-item label="菜单类型：0-内部菜单 1-外链菜单 2-重定向菜单 3-iframe嵌入">
          {{ formData.menuType }}
        </t-descriptions-item>
        <t-descriptions-item label="打开方式：0-当前窗口 1-新窗口打开">
          {{ formData.openTarget }}
        </t-descriptions-item>
        <t-descriptions-item label="iframe属性">
          {{ formData.iframeAttrs }}
        </t-descriptions-item>
        <t-descriptions-item label="菜单标题">
          {{ formData.title }}
        </t-descriptions-item>
        <t-descriptions-item label="菜单图标">
          {{ formData.icon }}
        </t-descriptions-item>
        <t-descriptions-item label="排序">
          {{ formData.sort }}
        </t-descriptions-item>
        <t-descriptions-item label="缓存">
          {{ formData.keepAlive }}
        </t-descriptions-item>
        <t-descriptions-item label="可见">
          {{ formData.visible }}
        </t-descriptions-item>
        <t-descriptions-item label="钉钉">
          {{ formData.pined }}
        </t-descriptions-item>
        <t-descriptions-item label="无标签页">
          {{ formData.withoutTab }}
        </t-descriptions-item>
        <t-descriptions-item label="头部参数">
          {{ formData.parameters }}
        </t-descriptions-item>
        <t-descriptions-item label="路由参数">
          {{ formData.extraParams }}
        </t-descriptions-item>
      </t-descriptions>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
