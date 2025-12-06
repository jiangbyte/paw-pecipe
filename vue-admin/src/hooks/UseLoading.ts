export function useLoading() {
  const isLoading = ref(false)

  const loadingStart = () => {
    isLoading.value = true
  }

  const loadingEnd = () => {
    isLoading.value = false
  }

  // 自动管理 loading 状态的便捷方法
  const withLoading = async <T>(promise: Promise<T>): Promise<T> => {
    loadingStart()
    try {
      const result = await promise
      return result
    } finally {
      loadingEnd()
    }
  }

  return {
    isLoading,
    loadingStart,
    loadingEnd,
    withLoading
  }
}
