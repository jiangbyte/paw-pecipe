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
    title: '用户名',
    colKey: 'username',
    width: 200,
    ellipsis: true
  },
  {
    title: '邮箱地址',
    colKey: 'email',
    width: 200,
    ellipsis: true
  },
  {
    title: '手机号码',
    colKey: 'telephone',
    width: 200,
    ellipsis: true
  },
  {
    title: '账户状态',
    colKey: 'statusName',
    width: 120,
    ellipsis: true
  },
  {
    title: '密码强度等级',
    colKey: 'passwordStrength',
    width: 120,
    ellipsis: true
  },
  {
    title: '最后修改密码的时间',
    colKey: 'lastPasswordChange',
    width: 180,
    ellipsis: true
  },
  {
    title: '最后登录时间',
    colKey: 'lastLoginTime',
    width: 180,
    ellipsis: true
  },
  {
    title: '最后登录IP地址',
    colKey: 'lastLoginIp',
    width: 180,
    ellipsis: true
  },
  {
    title: '登录次数统计',
    colKey: 'loginCount',
    width: 120,
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
