package io.jiangbyte.app.biz.sku.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.biz.sku.dto.BizProductSkuWithProductDto;
import io.jiangbyte.app.biz.sku.entity.BizProductSku;
import io.jiangbyte.app.biz.sku.dto.BizProductSkuDto;
import io.jiangbyte.app.biz.sku.dto.BizProductSkuPageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 商品日常销售SKU表 服务类
*/
public interface BizProductSkuService extends IService<BizProductSku> {
    Page<BizProductSku> page(BizProductSkuPageQuery req);

    Page<BizProductSkuWithProductDto> pageWithProduct(BizProductSkuPageQuery req);

    BizProductSkuWithProductDto detailWithProduct(String id);

    void add(BizProductSkuDto req);

    void edit(BizProductSkuDto req);

    void delete(List<String> ids);

    BizProductSku detail(String id);

    List<BizProductSku> latest(int n);

    List<BizProductSku> topN(int n);
}