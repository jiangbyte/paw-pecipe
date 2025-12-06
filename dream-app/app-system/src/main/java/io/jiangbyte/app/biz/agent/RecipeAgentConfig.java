// src/main/java/io/jiangbyte/app/biz/agent/config/RecipeAgentConfig.java
package io.jiangbyte.app.biz.agent;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "recipe.agents")
public class RecipeAgentConfig {
    private List<AgentDefinition> definitions;

    @Data
    public static class AgentDefinition {
        private String name;           // 如 home_cook
        private String displayName;    // 如 "家常菜"
        private String author;           // 角色名称
        private String avatar;  // 头像
        private String systemPromptTemplate;
        private boolean enabled = true;
        private Map<String, Object> metadata; // 保留扩展字段，如 cuisineRegion: "川菜"
    }
}