package io.jiangbyte.app.biz.recipecategory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.biz.recipecategory.dto.BizRecipeCategoryDto;
import io.jiangbyte.app.biz.recipecategory.dto.BizRecipeCategoryPageQuery;
import io.jiangbyte.app.biz.recipecategory.entity.BizRecipeCategory;
import io.jiangbyte.framework.option.LabelOption;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 菜谱分类表 服务类
*/
public interface BizRecipeCategoryService extends IService<BizRecipeCategory> {
    Page<BizRecipeCategory> page(BizRecipeCategoryPageQuery req);

    void add(BizRecipeCategoryDto req);

    void edit(BizRecipeCategoryDto req);

    void delete(List<String> ids);

    BizRecipeCategory detail(String id);

    List<BizRecipeCategory> latest(int n);

    List<BizRecipeCategory> topN(int n);

    List<LabelOption<String>> lists();
}