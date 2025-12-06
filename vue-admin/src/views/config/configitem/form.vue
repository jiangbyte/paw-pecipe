<script lang="ts" setup>
  import { useConfigItemApi } from '@/api'
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
      const { data, success } = await withLoading(useConfigItemApi().GetConfigItem(row?.id))
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
    const api = isEdit.value ? useConfigItemApi().EditConfigItem : useConfigItemApi().AddConfigItem

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
        <t-form-item label="分组编码" name="groupCode">
          <t-input v-model="formData.groupCode" placeholder="请输入分组Code" />
        </t-form-item>
        <t-form-item label="配置项名称" name="name">
          <t-input v-model="formData.name" placeholder="请输入配置项名称" />
        </t-form-item>
        <t-form-item label="配置项代码" name="code">
          <t-input v-model="formData.code" placeholder="请输入配置项代码" />
        </t-form-item>
        <t-form-item label="配置值" name="value">
          <t-input v-model="formData.value" placeholder="请输入配置值" />
        </t-form-item>
        <t-form-item label="组件类型" name="componentType">
          <t-input v-model="formData.componentType" placeholder="请输入组件类型" />
        </t-form-item>
        <t-form-item label="配置描述" name="description">
          <t-input v-model="formData.description" placeholder="请输入配置描述" />
        </t-form-item>
        <t-form-item label="排序" name="sort">
          <t-input v-model="formData.sort" placeholder="请输入排序" />
        </t-form-item>
      </t-form>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
