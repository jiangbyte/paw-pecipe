import antfu from '@antfu/eslint-config'

// https://github.com/antfu/eslint-config
export default antfu(
  {
    stylistic: true,
    // 启用 Vue 相关规则
    vue: {
      overrides: {
        'vue/no-unused-refs': 'off',
        'vue/no-reserved-component-names': 'off',
        'vue/component-definition-name-casing': 'off',
      },
    },
    typescript: {
      overrides: {
        'perfectionist/sort-exports': 'off',
        'perfectionist/sort-imports': 'off',
        'ts/no-unused-expressions': ['error', { allowShortCircuit: true }],
      },
    },
    rules: {
      'no-console': 'off', // 全局禁用 no-console 规则
    },
  },
)
