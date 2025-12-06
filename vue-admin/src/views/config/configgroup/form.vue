<script lang="ts" setup>
  import { useConfigGroupApi } from '@/api'
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
      const { data, success } = await withLoading(useConfigGroupApi().GetConfigGroup(row?.id))
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
    const api = isEdit.value
      ? useConfigGroupApi().EditConfigGroup
      : useConfigGroupApi().AddConfigGroup

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
        <t-form-item label="分组名称" name="name">
          <t-input v-model="formData.name" placeholder="请输入分组名称" />
        </t-form-item>
        <t-form-item label="分组代码" name="code">
          <t-input v-model="formData.code" placeholder="请输入分组代码" />
        </t-form-item>
        <t-form-item label="分组描述" name="description">
          <t-input v-model="formData.description" placeholder="请输入分组描述" />
        </t-form-item>
        <t-form-item label="排序" name="sort">
          <t-input v-model="formData.sort" placeholder="请输入排序" />
        </t-form-item>
        <t-form-item label="系统分组" name="isSystem">
          <t-radio-group v-model="formData.isSystem" :default-value="formData.isSystem">
            <t-radio :value="true">是</t-radio>
            <t-radio :value="false">否</t-radio>
          </t-radio-group>
        </t-form-item>
      </t-form>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
