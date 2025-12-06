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
    title: '账户ID',
    colKey: 'accountId',
    width: 120,
    ellipsis: true
  },
  {
    title: '主题',
    colKey: 'theme',
    width: 120,
    ellipsis: true
  },
  {
    title: '系统语言',
    colKey: 'language',
    width: 120,
    ellipsis: true
  },
  {
    title: '邮件通知',
    colKey: 'emailNotifications',
    width: 120,
    ellipsis: true
  },
  {
    title: '推送通知',
    colKey: 'pushNotifications',
    width: 120,
    ellipsis: true
  },
  {
    title: '允许私信',
    colKey: 'allowDirectMessage',
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
