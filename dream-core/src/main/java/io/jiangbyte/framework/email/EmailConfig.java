package io.jiangbyte.framework.email;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "app.email")
public class EmailConfig {
    private String from;
    private String nickname;
    private String resetUrlPrefix;
}