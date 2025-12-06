<script lang="ts" setup>
import { use${entity}Api } from '@/api'
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
    const { data, success } = await withLoading(use${entity}Api().Get${entity}(row?.id))
    if (success) {
      Object.assign(formData, data)
    }
    else {
      closeDrawer()
    }
  }
  else {
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
    ? use${entity}Api().Edit${entity}
    : use${entity}Api().Add${entity}

  const { success } = await withLoading(api(formData))
  if (success) {
    closeDrawer()
    emit('submit')
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
    :close-on-overlay-click="false"
    :confirm-btn="{ disabled: isLoading }"
    size="large"
    destroy-on-close
    @close="doClose"
    @confirm="doSubmit"
  >
    <#noparse>
    <template #header>
      {{ isEdit ? `编辑${props.formName}` : `新增${props.formName}` }}
    </template>
    </#noparse>
    <t-loading size="small" :loading="isLoading" show-overlay class="w-full">
      <t-form :data="formData" label-align="left">
        <#list table.fields as field>
        <#if !["id", "isDeleted", "deletedAt", "deleteUser", "createdAt", "createUser", "updatedAt", "updateUser"]?seq_contains(field.propertyName)>
        <t-form-item label="${field.comment}" name="${field.propertyName}">
          <t-input v-model="formData.${field.propertyName}" placeholder="请输入${field.comment}" />
        </t-form-item>
        </#if>
        </#list>
      </t-form>
    </t-loading>
  </t-drawer>
</template>

<style scoped>

</style>
