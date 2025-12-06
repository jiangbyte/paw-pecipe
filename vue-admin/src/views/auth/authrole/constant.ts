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
    title: '角色名称',
    colKey: 'name',
    width: 120,
    ellipsis: true
  },
  {
    title: '角色编码',
    colKey: 'code',
    width: 120,
    ellipsis: true
  },
  {
    title: '数据权限范围',
    colKey: 'dataScope',
    width: 120,
    ellipsis: true
  },
  {
    title: '角色描述',
    colKey: 'description',
    width: 120,
    ellipsis: true
  },
  {
    title: '分配的用户组ID列表',
    colKey: 'assignGroupIds',
    width: 120,
    ellipsis: true
  },
  {
    title: '创建时间',
    colKey: 'createdAt',
    width: 120,
    ellipsis: true
  },
  {
    title: '更新时间',
    colKey: 'updatedAt',
    width: 120,
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
