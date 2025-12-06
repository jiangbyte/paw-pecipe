import CryptoJS from 'crypto-js'

// utils/passwordCrypto.ts
const PASSWORD_SECRET_KEY = import.meta.env.VITE_PASSWORD_SECRET_KEY

export class PasswordUtil {
  /**
   * 加密密码，返回加密后的字符串
   */
  static encrypt(password: string): string {
    if (!password)
      return ''

    try {
      const keyBytes = this.stringToBytes(PASSWORD_SECRET_KEY)
      const passwordBytes = this.stringToBytes(password)

      // 使用RC4加密
      const encryptedBytes = this.rc4(passwordBytes, keyBytes)

      // 转换为Base64
      return this.bytesToBase64(encryptedBytes)
    }
    catch (error) {
      console.error('密码加密失败:', error)
      throw new Error('密码加密失败')
    }
  }

  /**
   * RC4流密码算法
   */
  private static rc4(data: Uint8Array, key: Uint8Array): Uint8Array {
    // 初始化S盒
    const S = new Uint8Array(256)
    for (let i = 0; i < 256; i++) {
      S[i] = i
    }

    // KSA - 密钥调度算法
    let j = 0
    for (let i = 0; i < 256; i++) {
      j = (j + S[i] + key[i % key.length]) % 256
      // 交换 S[i] 和 S[j]
      const temp = S[i]
      S[i] = S[j]
      S[j] = temp
    }

    // PRGA - 伪随机生成算法
    const result = new Uint8Array(data.length)
    let i = 0
    j = 0

    for (let k = 0; k < data.length; k++) {
      i = (i + 1) % 256
      j = (j + S[i]) % 256

      // 交换 S[i] 和 S[j]
      const temp = S[i]
      S[i] = S[j]
      S[j] = temp

      // 生成密钥流字节并加密
      const keyStreamByte = S[(S[i] + S[j]) % 256]
      result[k] = data[k] ^ keyStreamByte
    }

    return result
  }

  /**
   * 字符串转字节数组
   */
  private static stringToBytes(str: string): Uint8Array {
    const bytes = new Uint8Array(str.length)
    for (let i = 0; i < str.length; i++) {
      bytes[i] = str.charCodeAt(i) & 0xFF
    }
    return bytes
  }

  /**
   * 字节数组转Base64
   */
  private static bytesToBase64(bytes: Uint8Array): string {
    let binary = ''
    for (let i = 0; i < bytes.length; i++) {
      binary += String.fromCharCode(bytes[i])
    }
    return btoa(binary)
  }
}

const PATH_SECRET_KEY = import.meta.env.VITE_PATH_SECRET_KEY

/**
 * AES 路由路径加密工具类
 */
export class RouteCrypto {
  /**
   * 加密字符串
   * @param plainText 要加密的明文
   * @returns 加密后的密文
   */
  static encrypt(plainText: string): string {
    if (!plainText)
      return ''
    return CryptoJS.AES.encrypt(plainText, PATH_SECRET_KEY).toString()
  }

  /**
   * 解密字符串
   * @param cipherText 要解密的密文
   * @returns 解密后的明文
   */
  static decrypt(cipherText: string): string {
    if (!cipherText)
      return ''
    const bytes = CryptoJS.AES.decrypt(cipherText, PATH_SECRET_KEY)
    return bytes.toString(CryptoJS.enc.Utf8)
  }
}
