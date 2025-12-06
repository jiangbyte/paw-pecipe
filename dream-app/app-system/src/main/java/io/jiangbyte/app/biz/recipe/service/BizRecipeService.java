package io.jiangbyte.app.biz.recipe.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.biz.recipe.dto.BizRecipeDto;
import io.jiangbyte.app.biz.recipe.dto.BizRecipePageQuery;
import io.jiangbyte.app.biz.recipe.entity.BizRecipe;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 菜谱表 服务类
*/
public interface BizRecipeService extends IService<BizRecipe> {
    Page<BizRecipe> page(BizRecipePageQuery req);

    void add(BizRecipeDto req);

    void edit(BizRecipeDto req);

    void delete(List<String> ids);

    BizRecipe detail(String id);

    List<BizRecipe> latest(int n);

    List<BizRecipe> topN(int n);


}