<script lang="ts" setup>
  import { useUserProfileApi } from '@/api'
  import { COLUMNS, SortOptions } from './constant'
  import { SortTypeOptions } from '@/constants'
  import { toIDArray } from '@/utils'
  import { useLoading } from '@/hooks'
  import Form from './form.vue'
  import Detail from './detail.vue'

  const pageData = ref({
    current: 1,
    pages: 1,
    records: [],
    size: 20,
    total: 0
  })

  const pageParams = reactive({
    current: 1,
    pageSize: 20,
    sortField: '',
    sortOrder: '',
    keyword: ''
  })

  const { isLoading, withLoading } = useLoading()

  async function loadPageData() {
    const { data } = await withLoading(useUserProfileApi().PageUserProfile(pageParams))
    pageData.value = data
  }
  loadPageData()

  const selectedRowKeys = ref([])
  function handleSelectChange(selectedKeys: any) {
    selectedRowKeys.value = selectedKeys
  }

  async function handleDelete(id: string | string[]) {
    const idArray = toIDArray(id)
    if (idArray.length === 0) {
      MessagePlugin.warning('请选择要删除的记录')
      return
    }
    const { success } = await useUserProfileApi().DeleteUserProfile(idArray)
    if (success) {
      loadPageData()
    }
  }

  function handleReset() {
    pageParams.keyword = ''
    loadPageData()
  }

  function handlePageChange(pageInfo: any) {
    pageParams.current = pageInfo.current
    pageParams.pageSize = pageInfo.pageSize
    loadPageData()
  }

  const formRef = ref()
  const detailRef = ref()
  const formName = '用户档案详情'

  const columnControllerVisible = ref(false)
</script>

<template>
  <div>
    <div class="h-24 flex flex-col gap-2 p-4 justify-center">
      <div class="flex items-center gap-4 flex-1">
        <div class="flex items-center gap-2">
          <span class="w-14">关键字</span>
          <t-input v-model="pageParams.keyword" clearable class="w-40" />
        </div>

        <div class="flex items-center gap-2">
          <t-button theme="primary" @click="loadPageData">搜索</t-button>
          <t-button theme="default" @click="handleReset">重置</t-button>
        </div>
      </div>

      <!-- 第二行：操作按钮 -->
      <div class="flex items-center justify-between gap-2">
        <div class="flex items-center gap-2">
          <t-button theme="primary" @click="formRef.doOpen()">新增</t-button>
          <t-button variant="base" theme="danger" @click="handleDelete(selectedRowKeys)">
            删除
          </t-button>
        </div>
        <div class="flex items-center gap-2">
          <t-select
            v-model="pageParams.sortField"
            :borderless="true"
            placeholder="排序字段"
            clearable
            :options="SortOptions"
            style="width: 100px"
            @change="loadPageData"
          />
          <t-select
            v-model="pageParams.sortOrder"
            :borderless="true"
            placeholder="排序方式"
            clearable
            :options="SortTypeOptions"
            style="width: 100px"
            @change="loadPageData"
          />
          <t-button variant="text" theme="default" @click="columnControllerVisible = true">
            显示
          </t-button>
          <t-button variant="text" theme="default" @click="loadPageData">刷新</t-button>
        </div>
      </div>
    </div>

    <t-enhanced-table
      v-model:column-controller-visible="columnControllerVisible"
      :columns="COLUMNS"
      :column-controller="{
        hideTriggerButton: true
      }"
      :data="pageData.records"
      row-key="id"
      :hover="true"
      :loading="isLoading"
      :pagination="{
        current: pageData.current,
        pageSize: pageData.size,
        total: pageData.total,
        theme: 'simple'
      }"
      :selected-row-keys="selectedRowKeys"
      :tree="{
        treeNodeColumnIndex: 1,
        checkStrictly: false,
        indent: 25
      }"
      max-height="calc(100vh - 56px - 96px - 64px)"
      height="calc(100vh - 56px - 96px - 64px)"
      @select-change="handleSelectChange"
      @page-change="handlePageChange"
    >
      <template #operation="{ row }">
        <t-space :size="12" align="center">
          <t-link variant="text" theme="primary" @click="formRef.doOpen(row)">编辑</t-link>
          <t-link variant="text" theme="primary" @click="detailRef.doOpen(row)">详情</t-link>
          <t-popconfirm content="确认删除吗" @confirm="handleDelete(row.id)">
            <t-link variant="text" theme="danger">删除</t-link>
          </t-popconfirm>
        </t-space>
      </template>
    </t-enhanced-table>

    <Form ref="formRef" :form-name="formName" @submit="loadPageData" />
    <Detail ref="detailRef" :form-name="formName" />
  </div>
</template>

<style scoped></style>
