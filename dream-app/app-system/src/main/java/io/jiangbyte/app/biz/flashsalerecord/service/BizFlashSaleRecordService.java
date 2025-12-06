package io.jiangbyte.app.biz.flashsalerecord.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.biz.flashsalerecord.dto.FlashSaleRecordReq;
import io.jiangbyte.app.biz.flashsalerecord.entity.BizFlashSaleRecord;
import io.jiangbyte.app.biz.flashsalerecord.dto.BizFlashSaleRecordDto;
import io.jiangbyte.app.biz.flashsalerecord.dto.BizFlashSaleRecordPageQuery;

import java.util.List;

/**
 * @author Charlie Zhang
 * @version v1.0
 * @date 2025-12-03
 * @description 秒杀资格记录表 服务类
 */
public interface BizFlashSaleRecordService extends IService<BizFlashSaleRecord> {
    Page<BizFlashSaleRecord> page(BizFlashSaleRecordPageQuery req);

    void add(BizFlashSaleRecordDto req);

    void edit(BizFlashSaleRecordDto req);

    void delete(List<String> ids);

    BizFlashSaleRecord detail(String id);

    List<BizFlashSaleRecord> latest(int n);

    List<BizFlashSaleRecord> topN(int n);

    /**
     * 尝试获取秒杀资格
     *
     * @return 是否成功获得资格
     */
    boolean tryAcquireFlashSaleRecord(FlashSaleRecordReq req);

    /**
     * 标记资格已下单（绑定订单）
     *
     * @param recordId 秒杀记录ID
     * @param orderId  订单ID
     */
    void markAsOrdered(String recordId, String orderId);

    /**
     * 检查用户是否已有有效资格（防止重复抢）
     */
    boolean hasValidRecord(String userId, String flashId);
}