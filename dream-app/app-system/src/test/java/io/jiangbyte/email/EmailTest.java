package io.jiangbyte.email;

import io.jiangbyte.framework.email.EmailService;
import io.jiangbyte.app.App;
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
public class EmailTest {
    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail() throws Exception {
    }
}
