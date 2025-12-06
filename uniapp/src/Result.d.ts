// 通用返回接口
interface IResult<T> {
  code: string | number
  data: T | null
  message: string
  success: boolean
  time?: string
}

// 分页列表返回结果，IPageResult 使用
interface IListResult<T> {
  current: number
  pages: number
  records: T[]
  size: number
  total: number
}

// 分页返回结果
interface IPageResult<T> {
  code: string | number
  data: IListResult<T> | null
  message: string
  success: boolean
  time?: string
}
