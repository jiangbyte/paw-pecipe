package io.jiangbyte.app.biz.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.biz.product.entity.BizProduct;
import io.jiangbyte.app.biz.product.dto.BizProductDto;
import io.jiangbyte.app.biz.product.dto.BizProductPageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 商品主表 服务类
*/
public interface BizProductService extends IService<BizProduct> {
    Page<BizProduct> page(BizProductPageQuery req);

    void add(BizProductDto req);

    void edit(BizProductDto req);

    void delete(List<String> ids);

    BizProduct detail(String id);

    List<BizProduct> latest(int n);

    List<BizProduct> topN(int n);
}