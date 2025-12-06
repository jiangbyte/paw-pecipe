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
    title: '真实姓名',
    colKey: 'realName',
    width: 120,
    ellipsis: true
  },
  {
    title: '学校',
    colKey: 'school',
    width: 120,
    ellipsis: true
  },
  {
    title: '专业',
    colKey: 'major',
    width: 120,
    ellipsis: true
  },
  {
    title: '学号',
    colKey: 'studentId',
    width: 120,
    ellipsis: true
  },
  {
    title: '公司',
    colKey: 'company',
    width: 120,
    ellipsis: true
  },
  {
    title: '职位',
    colKey: 'jobTitle',
    width: 120,
    ellipsis: true
  },
  {
    title: '行业',
    colKey: 'industry',
    width: 120,
    ellipsis: true
  },
  {
    title: '国家',
    colKey: 'country',
    width: 120,
    ellipsis: true
  },
  {
    title: '省份',
    colKey: 'province',
    width: 120,
    ellipsis: true
  },
  {
    title: '城市',
    colKey: 'city',
    width: 120,
    ellipsis: true
  },
  {
    title: '详细地址',
    colKey: 'location',
    width: 120,
    ellipsis: true
  },
  {
    title: 'QQ',
    colKey: 'qq',
    width: 120,
    ellipsis: true
  },
  {
    title: '微信',
    colKey: 'wechat',
    width: 120,
    ellipsis: true
  },
  {
    title: '是否显示生日',
    colKey: 'showBirthday',
    width: 120,
    ellipsis: true
  },
  {
    title: '是否显示地理位置',
    colKey: 'showLocation',
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
