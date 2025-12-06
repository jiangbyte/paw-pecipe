// SpringEmailServiceImpl.java
package io.jiangbyte.framework.email;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpringEmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailConfig emailConfig;

    @Override
    public void sendPasswordResetEmail(String to, String token) throws Exception {
        String subject = "密码重置请求";

        // 使用配置中的URL前缀拼接完整的重置URL
        String resetURL = String.format("%s?token=%s", emailConfig.getResetUrlPrefix(), token);

        // 加载模板
        String template = loadTemplate("password_reset.html");

        // 替换模板变量
        String body = template.replace("{{ResetURL}}", resetURL)
                             .replace("{{Nickname}}", emailConfig.getNickname());

        sendEmail(to, subject, body);
    }

    @Override
    public void sendEmail(String to, String subject, String body) throws Exception {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // 设置发件人（包含昵称）
            helper.setFrom(emailConfig.getFrom(), emailConfig.getNickname());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true表示HTML内容

            mailSender.send(message);
            
            log.debug("邮件发送成功 - 收件人: {}, 主题: {}", to, subject);
            
        } catch (Exception e) {
            log.error("邮件发送失败 - 收件人: {}, 错误: {}", to, e.getMessage());
            throw new Exception("邮件发送失败: " + e.getMessage());
        }
    }

    /**
     * 加载HTML模板
     */
    private String loadTemplate(String templateName) throws Exception {
        try {
            ClassPathResource resource = new ClassPathResource("templates/email/" + templateName);
            byte[] content = FileCopyUtils.copyToByteArray(resource.getInputStream());
            return new String(content, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("加载邮件模板失败: {}", templateName, e);
            // 返回默认模板
            return "";
        }
    }
}