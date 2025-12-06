package io.jiangbyte.app.biz.agent;

import io.jiangbyte.app.biz.recipe.dto.BizRecipeDto;
import io.jiangbyte.app.biz.recipe.entity.BizRecipe;
import io.jiangbyte.framework.exception.BusinessException;
import io.jiangbyte.framework.result.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RecipeController {

    private final RecipeWorkflowService recipeWorkflowService;

    /**
     * 生成多维度个性化菜谱
     *
     * @param request 用户请求参数（必填 vegetable，其余可选）
     * @return 菜谱列表
     */
    @PostMapping("/recipes/generate")
    public Result<?> generateRecipes(@Valid @RequestBody RecipeRequest request) {
        try {
            log.info("Received recipe generation request: {}", request);
            List<BizRecipe> recipes = recipeWorkflowService.generateRecipes(request);
            return Result.success(recipes);
        } catch (Exception e) {
            log.error("Failed to generate recipes", e);
            throw new BusinessException("Failed to generate recipes", e);
        }
    }
}