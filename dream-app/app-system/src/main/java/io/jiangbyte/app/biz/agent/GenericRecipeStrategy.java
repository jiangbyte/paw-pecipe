// src/main/java/io/jiangbyte/app/biz/agent/strategy/GenericRecipeStrategy.java
package io.jiangbyte.app.biz.agent;

import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import io.jiangbyte.app.biz.recipe.dto.BizRecipeDto;
import org.dromara.common.spring.SpringContextUtil;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
public class GenericRecipeStrategy implements RecipeStrategy {

    final String SYSTEM_MESSAGE = """

            请严格按照以下要求生成菜谱：

            1. 标题：吸引人且体现特色
            2. 描述（description）：
               - 至少 100 字，说明风味特点、营养价值、适用人群
               - 必须体现用户要求的 {oilLevel}、{speed} 等偏好
               - 举例："本菜采用仅5ml橄榄油，保留西红柿天然酸甜，适合减脂期人群。"
            3. 食材（ingredients）：
               - 每项包含 name 和 amount，amount 需具体（如“2个”“5克”“1茶匙”）
            4. 步骤（steps）：
               - 每步 desc 至少 100 字，包含操作细节、火候、技巧
               - 示例："中小火加热不粘锅，倒入蛋液后轻轻晃动锅体使蛋液均匀铺开，待边缘微凝时翻面。"
            5. 小贴士（tips）：
               - 至少 2 条，每条 20~40 字；
               - 针对性建议：如少油做法、地域风味调整等

            6. 输出格式：
               - 必须返回一个合法的 JSON 对象，字段包括：
                 title, description, cover（调用getImage获取封面）,
                 author（为{author}）, avatar（为{avatar}）, duration, difficulty, categoryId（调用getCategoryOrSave获取分类ID）,
                 ingredients（数组，含name/amount）, steps（数组，含id/desc）, tips（字符串数组）。
               - 不要包含任何额外文本、解释或 markdown。
            """;

    @Override
    public ReactAgent buildAgent(ChatModel chatModel, RecipeAgentConfig.AgentDefinition def, RecipeRequest request) {
        // 构建 prompt，支持动态插入用户偏好
        String prompt = buildPrompt(def, request);

        // 获取 CategoryToolConfig 中定义的工具
        Function<String, String> getCategoryOrSaveTool = SpringContextUtil.getBeanByClass(CategoryToolConfig.class).getCategoryOrSave();
        FunctionToolCallback<String, String> getCategoryOrSave = FunctionToolCallback
                .builder("getCategoryOrSave", getCategoryOrSaveTool)
                .inputType(String.class)
                .description("根据菜谱分类名称获取或创建分类，并返回其ID")
                .build();

        // 图片工具
        Function<String, String> getImageTool = SpringContextUtil.getBeanByClass(RecipeImageToolConfig.class).getImage();
        FunctionToolCallback<String, String> getImage = FunctionToolCallback
                .builder("getImage", getImageTool)
                .inputType(String.class)
                .description("根据菜谱名称获取封面图片，例如西红柿炒鸡蛋、麻婆豆腐等，如果是少油版西红柿泥蛋羹这样的菜谱，通过关键的词进行搜索，例如泥蛋羹")
                .build();

        return ReactAgent.builder()
                .name(def.getName())
                .model(chatModel)
                .tools(getCategoryOrSave, getImage)
                .systemPrompt(prompt)
                .outputType(BizRecipeDto.class)
                .outputKey(def.getName() + "_recipe")
                .build();
    }

    private String buildPrompt(RecipeAgentConfig.AgentDefinition def, RecipeRequest req) {
        // 构建上下文变量
        Map<String, Object> variables = Map.of(
                "vegetable", req.getVegetable(),
                "oilLevel", req.getOilLevel() != null ? req.getOilLevel() : "正常",
                "speed", req.getSpeed() != null ? req.getSpeed() : "不限时间",
                "difficulty", req.getDifficulty() != null ? req.getDifficulty() : "中等",
                "region", req.getRegion() != null ? req.getRegion() : "中式家常",
                "author", def.getAuthor() != null ? def.getAuthor() : "未知作者",
                "avatar", def.getAvatar() != null ? def.getAvatar() : ""
        );

        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(def.getSystemPromptTemplate() + SYSTEM_MESSAGE);
        Message systemMessage = systemPromptTemplate.createMessage(variables);

        return systemMessage.getText();
    }
}