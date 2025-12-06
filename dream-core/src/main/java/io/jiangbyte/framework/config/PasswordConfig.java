package io.jiangbyte.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhangJiangHu
 * @version v1.0
 * @date 26/11/2025
 * @description TODO
 */
@Configuration
@ConfigurationProperties(prefix = "app.password")
@Data
public class PasswordConfig {
    private String secretKey;
}
