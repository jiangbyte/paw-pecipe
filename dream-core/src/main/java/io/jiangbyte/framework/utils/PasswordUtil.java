package io.jiangbyte.framework.utils;

import io.jiangbyte.framework.config.PasswordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Base64;

@Component
public class PasswordUtil {

    private final String secretKey;

    @Autowired
    public PasswordUtil(PasswordConfig passwordCryptoConfig) {
        this.secretKey = passwordCryptoConfig.getSecretKey();
    }

    /**
     * 解密密码 - 返回原始密码
     */
    public String decrypt(String encryptedText) {
        if (encryptedText == null || encryptedText.isEmpty()) {
            return "";
        }

        try {
            // Base64解码
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] keyBytes = secretKey.getBytes("UTF-8");

            // RC4解密（加密和解密使用相同的算法）
            byte[] decryptedBytes = rc4(encryptedBytes, keyBytes);

            // 转换为字符串
            return new String(decryptedBytes, "UTF-8");

        } catch (Exception e) {
            throw new RuntimeException("密码解密失败: " + e.getMessage(), e);
        }
    }

    /**
     * RC4流密码算法
     */
    private byte[] rc4(byte[] data, byte[] key) {
        // 初始化S盒
        int[] S = new int[256];
        for (int i = 0; i < 256; i++) {
            S[i] = i;
        }

        // KSA - 密钥调度算法
        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + S[i] + key[i % key.length]) % 256;
            // 交换 S[i] 和 S[j]
            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;
        }

        // PRGA - 伪随机生成算法
        byte[] result = new byte[data.length];
        int i = 0;
        j = 0;

        for (int k = 0; k < data.length; k++) {
            i = (i + 1) % 256;
            j = (j + S[i]) % 256;

            // 交换 S[i] 和 S[j]
            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;

            // 生成密钥流字节并解密
            int keyStreamByte = S[(S[i] + S[j]) % 256];
            result[k] = (byte) (data[k] ^ keyStreamByte);
        }

        return result;
    }
}