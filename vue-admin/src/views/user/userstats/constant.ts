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
    title: '等级',
    colKey: 'level',
    width: 120,
    ellipsis: true
  },
  {
    title: '经验值',
    colKey: 'exp',
    width: 120,
    ellipsis: true
  },
  {
    title: '累计经验值',
    colKey: 'totalExp',
    width: 120,
    ellipsis: true
  },
  {
    title: '登录天数',
    colKey: 'loginDays',
    width: 120,
    ellipsis: true
  },
  {
    title: '连续登录天数',
    colKey: 'continuousLoginDays',
    width: 120,
    ellipsis: true
  },
  {
    title: '发帖数',
    colKey: 'postCount',
    width: 120,
    ellipsis: true
  },
  {
    title: '评论数',
    colKey: 'commentCount',
    width: 120,
    ellipsis: true
  },
  {
    title: '获赞数',
    colKey: 'likeCount',
    width: 120,
    ellipsis: true
  },
  {
    title: '关注数',
    colKey: 'followCount',
    width: 120,
    ellipsis: true
  },
  {
    title: '粉丝数',
    colKey: 'fansCount',
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
