<script lang="ts" setup>
  import { useSysDictApi } from '@/api'
  import { toIDArray } from '@/utils'
  import { useLoading } from '@/hooks'
  import { COLUMNS, SortOptions } from './constant'
  import { SortTypeOptions } from '@/constants'
  import Form from './form.vue'
  import TypeForm from './typeform.vue'
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
    keyword: '',
    dictType: ''
  })

  const { isLoading, withLoading } = useLoading()
  const { isLoading: treeOptionsLoading, withLoading: withTreeOptionsLoading } = useLoading()

  async function loadPageData() {
    const { data } = await withLoading(useSysDictApi().PageSysDict(pageParams))
    pageData.value = data
  }

  const treeOptionKeyword = ref('')
  const treeOptions = ref([])
  async function loadTreeOptions() {
    const { data } = await withTreeOptionsLoading(
      useSysDictApi().TreeOptions(treeOptionKeyword.value)
    )
    treeOptions.value = data
  }

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
    const { success } = await useSysDictApi().DeleteSysDict(idArray)
    if (success) {
      loadTreeOptions()
    }
  }

  function loadData() {
    loadPageData()
    loadTreeOptions()
  }

  loadData()

  function handleReset() {
    pageParams.keyword = ''
    loadTreeOptions()
  }

  function handlePageChange(pageInfo: any) {
    pageParams.current = pageInfo.current
    pageParams.pageSize = pageInfo.pageSize
    loadPageData()
  }

  const formRef = ref()
  const typeFormRef = ref()
  const detailRef = ref()
  const formName = '系统字典'

  const columnControllerVisible = ref(false)

  async function treeNodeClickHandler(value: Array<any>, context: any) {
    const { node } = context
    if (node.data.value) {
      console.log(node.data.value)
      pageParams.dictType = node.data.value
      loadPageData()
    }
  }
</script>

<template>
  <div class="flex">
    <div class="w-60 flex-shrink-0 p-2">
      <div class="flex flex-col gap-2">
        <div class="flex items-center gap-2">
          <span class="w-14">关键字</span>
          <t-input v-model="treeOptionKeyword" clearable class="w-40" />
        </div>
        <div class="flex items-center justify-between gap-2 w-full">
          <t-button theme="primary" @click="typeFormRef.doOpen()">新增</t-button>
          <div class="flex items-center gap-2">
            <t-button theme="primary" @click="loadTreeOptions">搜索</t-button>
            <t-button
              theme="default"
              @click="
                () => {
                  treeOptionKeyword = ''
                  loadTreeOptions()
                }
              "
            >
              重置
            </t-button>
          </div>
        </div>
      </div>
      <t-loading size="small" :loading="treeOptionsLoading" show-overlay class="w-full">
        <t-tree
          :data="treeOptions"
          :keys="{ value: 'value', label: 'text', children: 'children' }"
          activable
          hover
          transition
          line
          @active="treeNodeClickHandler"
        />
      </t-loading>
    </div>
    <div class="flex-1">
      <div class="h-22 flex flex-col gap-2 p-4 justify-center">
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
        <div class="flex items-center justify-between gap-2">
          <div class="flex items-center gap-2">
            <t-button theme="primary" @click="formRef.doOpen(null, pageParams.dictType)">
              新增
            </t-button>
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
            <t-button variant="text" theme="default" @click="loadData">刷新</t-button>
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
        <template #sort="{ row }">
          <SortTag :value="row.sort" />
        </template>
        <template #operation="{ row }">
          <t-space :size="12" align="center">
            <t-link variant="text" theme="primary" @click="formRef.doOpen(row, null)">编辑</t-link>
            <t-link variant="text" theme="primary" @click="detailRef.doOpen(row)">详情</t-link>
            <t-popconfirm content="确认删除吗" @confirm="handleDelete(row.id)">
              <t-link variant="text" theme="danger">删除</t-link>
            </t-popconfirm>
          </t-space>
        </template>
      </t-enhanced-table>
      <Form ref="formRef" :form-name="formName" @submit="loadData" />
      <Detail ref="detailRef" :form-name="formName" />
      <TypeForm ref="typeFormRef" form-name="字典类型" @submit="loadData" />
    </div>
  </div>
</template>

<style scoped></style>
