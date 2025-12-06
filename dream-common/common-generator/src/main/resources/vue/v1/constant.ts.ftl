import type { DropdownOption, PrimaryTableCol } from 'tdesign-vue-next'

export const COLUMNS: PrimaryTableCol[] = [
  {
      colKey: 'row-select',
      title: '选择',
      type: 'multiple',
      width: 40,
      fixed: 'left',
  },
<#list table.fields as field>
<#if !["id", "isDeleted", "deletedAt", "deleteUser", "createUser", "updateUser"]?seq_contains(field.propertyName)>
  {
  title: '${field.comment}',
  colKey: '${field.propertyName}',
  width: 120,
  ellipsis: true,
  },
</#if>
</#list>
  {
      title: '操作',
      colKey: 'operation',
      width: 180,
      align: 'center',
      fixed: 'right',
  },
]

export const SortOptions: DropdownOption[] = [
  {
      value: 'id',
      label: 'ID',
  },
  ...COLUMNS
      .filter(column =>
        column.colKey !== 'row-select'
        && column.colKey !== 'operation'
        && column.title,
      )
      .map(column => ({
        value: column.colKey,
        label: column.title as string,
      })),
]
