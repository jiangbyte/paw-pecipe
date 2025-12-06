package io.jiangbyte.framework.email;

/**
 * @author ZhangJiangHu
 * @version v1.0
 * @date 18/11/2025
 * @description TODO
 */
public interface EmailService {
    void sendPasswordResetEmail(String to, String token) throws Exception;
    void sendEmail(String to, String subject, String body) throws Exception;
}