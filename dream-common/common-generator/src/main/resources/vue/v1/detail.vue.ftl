<script lang="ts" setup>
import { use${entity}Api } from '@/api'
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
    const { data, success } = await withLoading(use${entity}Api().Get${entity}(row?.id))
    if (success) {
      Object.assign(formData, data)
    }
    else {
      closeDrawer()
    }
 }
}

defineExpose({
  doOpen,
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
      <#noparse>
      <template #header>
        {{ `${props.formName}详情` }}
      </template>
      </#noparse>
      <t-loading size="small" :loading="isLoading" show-overlay class="w-full">
        <t-descriptions :column="1" colon table-layout="auto">
          <#list table.fields as field>
        <#if !["isDeleted","id", "deletedAt", "deleteUser", "createdAt", "createUser", "updatedAt", "updateUser"]?seq_contains(field.propertyName)>
          <t-descriptions-item label="${field.comment}">
              {{ formData.${field.propertyName} }}
          </t-descriptions-item>
        </#if>
          </#list>
        </t-descriptions>
      </t-loading>
  </t-drawer>
</template>

<style scoped>

</style>