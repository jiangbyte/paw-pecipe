package io.jiangbyte.app.biz.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.biz.order.entity.BizOrder;
import io.jiangbyte.app.biz.order.dto.BizOrderDto;
import io.jiangbyte.app.biz.order.dto.BizOrderPageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 订单主表 服务类
*/
public interface BizOrderService extends IService<BizOrder> {
    Page<BizOrder> page(BizOrderPageQuery req);

    void add(BizOrderDto req);

    void edit(BizOrderDto req);

    void delete(List<String> ids);

    BizOrder detail(String id);

    List<BizOrder> latest(int n);

    List<BizOrder> topN(int n);
}