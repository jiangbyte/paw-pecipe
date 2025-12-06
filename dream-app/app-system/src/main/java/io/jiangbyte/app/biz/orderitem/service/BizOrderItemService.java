package io.jiangbyte.app.biz.orderitem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.biz.orderitem.entity.BizOrderItem;
import io.jiangbyte.app.biz.orderitem.dto.BizOrderItemDto;
import io.jiangbyte.app.biz.orderitem.dto.BizOrderItemPageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 订单明细表 服务类
*/
public interface BizOrderItemService extends IService<BizOrderItem> {
    Page<BizOrderItem> page(BizOrderItemPageQuery req);

    void add(BizOrderItemDto req);

    void edit(BizOrderItemDto req);

    void delete(List<String> ids);

    BizOrderItem detail(String id);

    List<BizOrderItem> latest(int n);

    List<BizOrderItem> topN(int n);
}