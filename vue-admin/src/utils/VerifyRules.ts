// 用户名
export const VerifyUsernameRule = [
  { required: true, message: '请输入用户名', trigger: 'blur' as const },
  { min: 3, max: 20, message: '用户名长度在 3 到 20 个字符', trigger: 'change' as const },
  { pattern: /^[a-z]/i, message: '用户名必须以英文字母开头', trigger: 'change' as const },
  { pattern: /^[\w\-@]+$/, message: '用户名只能包含英文', trigger: 'change' as const }
]

// 密码
export const VerifyPasswordRule = [
  { required: true, message: '请输入密码', trigger: 'blur' as const },
  { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'change' as const },
  { pattern: /^[\w\-@]+$/, message: '密码只能包含英文', trigger: 'change' as const }
]

// 验证码
export const VerifyCapchaRule = [
  { required: true, message: '请输入验证码', trigger: 'blur' as const },
  { min: 4, max: 4, message: '验证码长度为 4 个字符', trigger: 'change' as const }
]

// 昵称
export const VerifyNicknameRule = [
  { required: true, message: '请输入昵称', trigger: 'blur' as const },
  { min: 2, max: 20, message: '昵称长度在 2 到 20 个字符', trigger: 'change' as const }
]

// 邮箱
export const VerifyEmailRule = [
  { required: true, message: '请输入邮箱', trigger: 'blur' as const },
  { type: 'email', message: '请输入正确的邮箱地址', trigger: 'change' as const }
]

// 手机号
export const VerifyPhoneRule = [
  { required: true, message: '请输入手机号', trigger: 'blur' as const },
  { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'change' as const }
]
