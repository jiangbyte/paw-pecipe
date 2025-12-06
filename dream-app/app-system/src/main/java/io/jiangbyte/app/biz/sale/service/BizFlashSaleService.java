package io.jiangbyte.app.biz.sale.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.biz.sale.entity.BizFlashSale;
import io.jiangbyte.app.biz.sale.dto.BizFlashSaleDto;
import io.jiangbyte.app.biz.sale.dto.BizFlashSalePageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 限时抢购活动表 服务类
*/
public interface BizFlashSaleService extends IService<BizFlashSale> {
    Page<BizFlashSale> page(BizFlashSalePageQuery req);

    void add(BizFlashSaleDto req);

    void edit(BizFlashSaleDto req);

    void delete(List<String> ids);

    BizFlashSale detail(String id);

    List<BizFlashSale> latest(int n);

    List<BizFlashSale> topN(int n);
}