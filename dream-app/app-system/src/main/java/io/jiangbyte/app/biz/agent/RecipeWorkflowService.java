// src/main/java/io/jiangbyte/app/biz/agent/RecipeWorkflowService.java
package io.jiangbyte.app.biz.agent;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.agent.flow.agent.ParallelAgent;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import com.alibaba.cloud.ai.graph.exception.GraphStateException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jiangbyte.app.biz.recipe.dto.BizRecipeDto;
import io.jiangbyte.app.biz.recipe.entity.BizRecipe;
import io.jiangbyte.app.biz.recipe.service.BizRecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.trans.service.impl.TransService;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeWorkflowService {

    private final ChatModel chatModel;
    private final ObjectMapper objectMapper;
    private final RecipeAgentFactory agentFactory;

    private final BizRecipeService bizRecipeService;

    private final TransService transService;

    public List<BizRecipe> generateRecipes(RecipeRequest request) throws GraphStateException, GraphRunnerException {
        if (request.getVegetable() == null || request.getVegetable().isBlank()) {
            throw new IllegalArgumentException("主食材不能为空");
        }

        // 1. 动态创建 Agents（基于 YAML + 用户参数）
        List<ReactAgent> agents = agentFactory.createAgents(chatModel, request);

        // 2. 并行执行
        ParallelAgent parallelAgent = ParallelAgent.builder()
                .name("recipe_parallel_agent")
                .subAgents(List.copyOf(agents))
                .mergeOutputKey("all_recipes")
                .build();

        UserMessage input = new UserMessage("请为蔬菜 '" + request.getVegetable() + "' 生成食谱");
        Optional<OverAllState> result = parallelAgent.invoke(input);

        if (result.isEmpty()) {
            throw new RuntimeException("Failed to generate recipes");
        }

        // 3. 解析结果
        var state = result.get();

        List<BizRecipe> bizRecipes = new ArrayList<>();
        String recognitionId = IdUtil.fastSimpleUUID();
        for (ReactAgent agent : agents) {
            String key = agent.getOutputKey();
            state.value(key).ifPresent(obj -> {
                try {
                    BizRecipeDto bizRecipeDto = objectMapper.readValue(((AssistantMessage) obj).getText(), BizRecipeDto.class);
                    BizRecipe bizRecipe = BeanUtil.copyProperties(bizRecipeDto, BizRecipe.class);
                    bizRecipe.setRecognitionId(recognitionId);
                    bizRecipe.setIsPublic(Boolean.FALSE);
                    bizRecipes.add(bizRecipe);
                } catch (JsonProcessingException e) {
                    log.error("Parse recipe error for key: {}", key, e);
                }
            });
        }

        bizRecipeService.saveBatch(bizRecipes);
        transService.transBatch(bizRecipes);
        log.info("Generated {} recipes for vegetable: {}", bizRecipes.size(), request.getVegetable());
        return bizRecipes;
    }
}