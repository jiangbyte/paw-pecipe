// src/main/java/io/jiangbyte/app/biz/agent/factory/RecipeAgentFactory.java
package io.jiangbyte.app.biz.agent;

import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecipeAgentFactory {

    private final RecipeAgentConfig config;
    private final GenericRecipeStrategy strategy;

    public RecipeAgentFactory(RecipeAgentConfig config, GenericRecipeStrategy strategy) {
        this.config = config;
        this.strategy = strategy;
    }

    public List<ReactAgent> createAgents(ChatModel chatModel, RecipeRequest request) {
        List<ReactAgent> agents = new ArrayList<>();
        for (var def : config.getDefinitions()) {
            if (!def.isEnabled()) continue;
            ReactAgent agent = strategy.buildAgent(chatModel, def, request);
            agents.add(agent);
        }
        return agents;
    }
}