package io.jiangbyte;

import io.jiangbyte.app.App;
import io.jiangbyte.framework.email.EmailService;
import io.jiangbyte.framework.utils.PasswordUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author ZhangJiangHu
 * @version v1.0
 * @date 18/11/2025
 * @description TODO
 */
@SpringBootTest(classes = App.class)
public class PasswordTest {
    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordUtil passwordCryptoUtil;

    @Test
    public void testCrypto() {
        try {
            // 测试一个已知的加密结果
            String testPassword = "123456";
            String testEncrypted = "UMe1Nj2R"; // 从前端获取一个实际的加密结果

            System.out.println("测试密码: " + testPassword);
            System.out.println("加密结果: " + testEncrypted);

            String decrypted = passwordCryptoUtil.decrypt(testEncrypted);
            System.out.println("解密结果: " + decrypted);
            System.out.println("匹配结果: " + testPassword.equals(decrypted));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
