import type { DropdownOption, PrimaryTableCol } from 'tdesign-vue-next'

export const COLUMNS: PrimaryTableCol[] = [
  {
    colKey: 'row-select',
    title: '选择',
    type: 'multiple',
    width: 40,
    fixed: 'left'
  },
  {
    title: '菜单标题',
    colKey: 'title',
    width: 180,
    ellipsis: true
  },
  {
    title: '菜单名称',
    colKey: 'name',
    width: 120,
    ellipsis: true
  },
  {
    title: '菜单路径',
    colKey: 'path',
    width: 120,
    ellipsis: true
  },
  {
    title: '菜单类型',
    colKey: 'menuType',
    width: 120,
    ellipsis: true
  },
  {
    title: '打开方式',
    colKey: 'openTarget',
    width: 120,
    ellipsis: true
  },
  {
    title: '排序',
    colKey: 'sort',
    width: 90,
    ellipsis: true
  },
  {
    title: '缓存',
    colKey: 'keepAlive',
    width: 90,
    ellipsis: true
  },
  {
    title: '可见',
    colKey: 'visible',
    width: 90,
    ellipsis: true
  },
  {
    title: '钉钉',
    colKey: 'pined',
    width: 90,
    ellipsis: true
  },
  {
    title: '标签页',
    colKey: 'withoutTab',
    width: 90,
    ellipsis: true
  },
  {
    title: '创建时间',
    colKey: 'createdAt',
    width: 180,
    ellipsis: true
  },
  {
    title: '更新时间',
    colKey: 'updatedAt',
    width: 180,
    ellipsis: true
  },
  {
    title: '操作',
    colKey: 'operation',
    width: 180,
    align: 'center',
    fixed: 'right'
  }
]

export const SortOptions: DropdownOption[] = [
  {
    value: 'id',
    label: 'ID'
  },
  ...COLUMNS.filter(
    column => column.colKey !== 'row-select' && column.colKey !== 'operation' && column.title
  ).map(column => ({
    value: column.colKey,
    label: column.title as string
  }))
]
