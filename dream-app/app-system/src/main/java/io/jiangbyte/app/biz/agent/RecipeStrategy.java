// src/main/java/io/jiangbyte/app/biz/agent/strategy/RecipeStrategy.java
package io.jiangbyte.app.biz.agent;

import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import org.springframework.ai.chat.model.ChatModel;

public interface RecipeStrategy {
    ReactAgent buildAgent(ChatModel chatModel, RecipeAgentConfig.AgentDefinition def, RecipeRequest request);
}