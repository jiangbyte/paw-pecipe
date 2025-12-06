package io.jiangbyte.app.biz.agent;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.jiangbyte.app.biz.recipecategory.entity.BizRecipeCategory;
import io.jiangbyte.app.biz.recipecategory.service.BizRecipeCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class CategoryToolConfig {

    private final BizRecipeCategoryService bizCategoryService;

    @Bean
    @Description("根据菜谱分类名称获取或创建分类，并返回其ID（字符串类型）")
    public Function<String, String> getCategoryOrSave() {
        return categoryName -> {
            log.info("菜谱分类: " + categoryName);
            LambdaQueryWrapper<BizRecipeCategory> query = new LambdaQueryWrapper<>();
            query.eq(BizRecipeCategory::getName, categoryName);

            String id = "";
            if (bizCategoryService.exists(query)) {
                BizRecipeCategory existing = bizCategoryService.getOne(query);
                id = existing.getId();
            } else {
                BizRecipeCategory newCategory = new BizRecipeCategory();
                newCategory.setName(categoryName);
                bizCategoryService.save(newCategory);
                id = newCategory.getId();
            }

            log.info("分类ID: " + id);
            return id;
        };
    }
}