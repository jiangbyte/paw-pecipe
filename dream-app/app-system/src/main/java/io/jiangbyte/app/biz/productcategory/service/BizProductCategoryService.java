package io.jiangbyte.app.biz.productcategory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.biz.productcategory.entity.BizProductCategory;
import io.jiangbyte.app.biz.productcategory.dto.BizProductCategoryDto;
import io.jiangbyte.app.biz.productcategory.dto.BizProductCategoryPageQuery;
import io.jiangbyte.framework.option.LabelOption;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 商品分类表 服务类
*/
public interface BizProductCategoryService extends IService<BizProductCategory> {
    Page<BizProductCategory> page(BizProductCategoryPageQuery req);

    void add(BizProductCategoryDto req);

    void edit(BizProductCategoryDto req);

    void delete(List<String> ids);

    BizProductCategory detail(String id);

    List<BizProductCategory> latest(int n);

    List<BizProductCategory> topN(int n);

    List<LabelOption<String>> lists();
}