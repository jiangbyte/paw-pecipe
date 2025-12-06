<script lang="ts" setup>
  import { useAuthGroupApi } from '@/api'
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
      const { data, success } = await withLoading(useAuthGroupApi().GetAuthGroup(row?.id))
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
    const api = isEdit.value ? useAuthGroupApi().EditAuthGroup : useAuthGroupApi().AddAuthGroup

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
        <t-form-item label="父级组ID" name="parentId">
          <t-input v-model="formData.parentId" placeholder="请输入父级组ID" />
        </t-form-item>
        <t-form-item label="用户组名称" name="name">
          <t-input v-model="formData.name" placeholder="请输入用户组名称" />
        </t-form-item>
        <t-form-item label="用户组编码" name="code">
          <t-input v-model="formData.code" placeholder="请输入用户组编码" />
        </t-form-item>
        <t-form-item label="用户组描述" name="description">
          <t-input v-model="formData.description" placeholder="请输入用户组描述" />
        </t-form-item>
        <t-form-item label="排序号" name="sort">
          <t-input v-model="formData.sort" placeholder="请输入排序号" />
        </t-form-item>
        <t-form-item label="管理员ID" name="adminId">
          <t-input v-model="formData.adminId" placeholder="请输入管理员ID" />
        </t-form-item>
        <t-form-item label="最大用户数量限制" name="maxUserCount">
          <t-input v-model="formData.maxUserCount" placeholder="请输入最大用户数量限制" />
        </t-form-item>
      </t-form>
    </t-loading>
  </t-drawer>
</template>

<style scoped></style>
