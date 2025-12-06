package io.jiangbyte.app.biz.flashsalerecord.dto;

public interface FlashSaleRecordStatus {
    int ACQUIRED = 0; // 已抢到未下单
    int ORDERED = 1;  // 已下单
    int PAID = 2;     // 已支付
    int EXPIRED = 3;  // 已失效
}