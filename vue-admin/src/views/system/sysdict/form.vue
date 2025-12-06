<script lang="ts" setup>
  import { useSysDictApi } from '@/api'
  import { useBoolean, useLoading } from '@/hooks'
  import { ResetFormData } from '@/utils'

  const props = defineProps<{
    formName?: string
  }>()

  const emit = defineEmits(['close', 'submit'])

  const { value: visible, setFalse: closeDrawer, setTrue: openDrawer } = useBoolean(false)
  const { value: isEdit, setFalse: setAddMode, setTrue: setEditMode } = useBoolean(false)
  const { isLoading, withLoading } = useLoading()
  const { isLoading: listTypeOptionsLoading, withLoading: withListTypeOptionsLoading } =
    useLoading()

  const formData = reactive<DataFormType>({})
  const typeOptions = ref([])

  async function doOpen(row: any, type: string) {
    openDrawer()
    ResetFormData(formData)
    formData.dictType = type

    const { data } = await withListTypeOptionsLoading(useSysDictApi().ListTypeOptions(''))
    typeOptions.value = data

    if (type) {
      const name = (typeOptions.value as TypeOption[]).find(item => item.value === type)?.text
      formData.typeLabel = name
    }

    if (row?.id) {
      setEditMode()
      const { data, success } = await withLoading(useSysDictApi().GetSysDict(row?.id))
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
    const api = isEdit.value ? useSysDictApi().EditSysDict : useSysDictApi().AddSysDict

    const { success } = await withLoading(api(formData))
    if (success) {
      closeDrawer()
      emit('submit')
    }
  }

  function changeType(value: any) {
    const name = (typeOptions.value as TypeOption[]).find(item => item.value === value)?.text
    formData.typeLabel = name
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
        <t-form-item label="字典类型编码" name="dictType">
          <t-tree-select
            v-model="formData.dictType"
            :keys="{ value: 'value', label: 'text' }"
            :data="typeOptions"
            placeholder="请选择字典类型编码"
            @change="changeType"
          />
        </t-form-item>
        <t-form-item label="字典值" name="dictValue">
          <t-input v-model="formData.dictValue" placeholder="请输入字典值" />
        </t-form-item>
        <t-form-item label="字典标签" name="dictLabel">
          <t-input v-model="formData.dictLabel" placeholder="请输入字典标签" />
        </t-form-item>
        <t-form-item label="排序号" name="sort">
          <t-input-number v-model="formData.sort" />
        </t-form-item>
      </t-form>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
