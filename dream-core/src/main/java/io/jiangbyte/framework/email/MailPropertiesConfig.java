package io.jiangbyte.framework.email;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class MailPropertiesConfig {
    
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String protocol;
    private Properties properties;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setProtocol(protocol);
        mailSender.setDefaultEncoding("UTF-8");
        
        if (properties != null) {
            mailSender.setJavaMailProperties(properties);
        }
        
        return mailSender;
    }
}
