package io.jiangbyte.app.biz.flashsalerecord.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.biz.flashsalerecord.FlashSaleStockUtil;
import io.jiangbyte.app.biz.flashsalerecord.FlashSaleUserRecordUtil;
import io.jiangbyte.app.biz.flashsalerecord.dto.FlashSaleRecordReq;
import io.jiangbyte.app.biz.flashsalerecord.dto.FlashSaleRecordStatus;
import io.jiangbyte.app.biz.flashsalerecord.entity.BizFlashSaleRecord;
import io.jiangbyte.app.biz.flashsalerecord.dto.BizFlashSaleRecordDto;
import io.jiangbyte.app.biz.flashsalerecord.dto.BizFlashSaleRecordPageQuery;
import io.jiangbyte.app.biz.flashsalerecord.mapper.BizFlashSaleRecordMapper;
import io.jiangbyte.app.biz.flashsalerecord.service.BizFlashSaleRecordService;
import io.jiangbyte.app.biz.sale.entity.BizFlashSale;
import io.jiangbyte.app.biz.sale.mapper.BizFlashSaleMapper;
import io.jiangbyte.app.biz.test.RecordMessage;
import io.jiangbyte.app.mq.FlashSaleRabbitConfig;
import io.jiangbyte.framework.utils.SortUtils;
import io.jiangbyte.framework.enums.ISortOrderEnum;
import io.jiangbyte.framework.exception.BusinessException;
import io.jiangbyte.framework.pojo.BasePageRequest;
import io.jiangbyte.framework.result.ResultCode;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

/**
 * @author Charlie Zhang
 * @version v1.0
 * @date 2025-06-23
 * @description 秒杀资格记录表 服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BizFlashSaleRecordServiceImpl extends ServiceImpl<BizFlashSaleRecordMapper, BizFlashSaleRecord> implements BizFlashSaleRecordService {
    private final BizFlashSaleMapper bizFlashSaleMapper;
    private final RabbitTemplate rabbitTemplate;

    private final RedisTemplate<String, Object> redisTemplate;

    private final FlashSaleUserRecordUtil userRecordUtil;

    private final FlashSaleStockUtil flashSaleStockUtil;

    @Override
    public Page<BizFlashSaleRecord> page(BizFlashSaleRecordPageQuery req) {
        QueryWrapper<BizFlashSaleRecord> queryWrapper = new QueryWrapper<BizFlashSaleRecord>().checkSqlInjection();
        SortUtils.handleSort(BizFlashSaleRecord.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizFlashSaleRecordDto req) {
        BizFlashSaleRecord bean = BeanUtil.toBean(req, BizFlashSaleRecord.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizFlashSaleRecordDto req) {
        if (!this.exists(new LambdaQueryWrapper<BizFlashSaleRecord>().eq(BizFlashSaleRecord::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        BizFlashSaleRecord bean = BeanUtil.toBean(req, BizFlashSaleRecord.class);
        BeanUtil.copyProperties(req, bean);
        this.updateById(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<String> ids) {
        if (ObjectUtil.isEmpty(ids)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        this.removeByIds(ids);
    }

    @Override
    public BizFlashSaleRecord detail(String id) {
        BizFlashSaleRecord bizFlashSaleRecord = this.getById(id);
        if (ObjectUtil.isEmpty(bizFlashSaleRecord)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return bizFlashSaleRecord;
    }

    @Override
    public List<BizFlashSaleRecord> latest(int n) {
        return this.list(new QueryWrapper<BizFlashSaleRecord>()
                .lambda()
                .orderByDesc(BizFlashSaleRecord::getId)
                .last("limit " + n));
    }

    @Override
    public List<BizFlashSaleRecord> topN(int n) {
        return this.list(new QueryWrapper<BizFlashSaleRecord>()
                .lambda()
                .orderByDesc(BizFlashSaleRecord::getId)
                .last("limit " + n));
    }

    @Override
    public boolean tryAcquireFlashSaleRecord(FlashSaleRecordReq req) {
        String userId = "1"; // TODO 模拟用户ID, 实际项目需要从上下文中获取
        String flashId = req.getFlashId();
        Integer quantity = req.getQuantity();

        // 1. 校验秒杀活动是否存在且正在进行
        BizFlashSale flashSale = bizFlashSaleMapper.selectById(flashId);
        if (flashSale == null || !Boolean.TRUE.equals(flashSale.getStatus())) {
            throw new BusinessException("秒杀活动不存在或未开启");
        }

        String productId = flashSale.getProductId();
        Date nowTime = new Date();
        if (nowTime.before(flashSale.getStartTime()) || nowTime.after(flashSale.getEndTime())) {
            throw new BusinessException("不在秒杀时间范围内");
        }

        // 2. Redis 快速检查：用户是否已参与（高性能拦截）
        if (userRecordUtil.hasParticipated(flashId, userId)) {
            return false; // 提前返回，不扣库存
        }

        // 3. 检查用户是否超过限购
        int limit = Optional.ofNullable(flashSale.getLimitPerUser()).orElse(1);
        if (quantity > limit) {
            quantity = limit;
        }

        // 4. Redis 原子扣库存
        if (!flashSaleStockUtil.tryDecreaseStock(flashId, quantity)) {
            throw new BusinessException("手慢了，库存不足！");
        }

        // 5. 创建资格记录（状态=0: 已抢到未下单）（库存已锁定）
        BizFlashSaleRecord record = new BizFlashSaleRecord();
        record.setUserId(userId);
        record.setFlashId(flashId);
        record.setProductId(productId);
        record.setQuantity(quantity);
        record.setStatus(FlashSaleRecordStatus.ACQUIRED);

        this.save(record);

        // 标记用户已参与（Redis）
        // 计算 Redis 用户记录的过期时间：活动结束时间 + 1天
        Date endTime = flashSale.getEndTime(); // 活动结束时间
        if (endTime == null) {
            endTime = new Date(System.currentTimeMillis() + 24 * 3600_000L); // 默认 24 小时后
        }

        long expireAtMillis = endTime.getTime() + 24 * 3600_000L; // +1 天
        long nowMillis = System.currentTimeMillis();
        long ttlMillis = Math.max(expireAtMillis - nowMillis, 3600_000L); // 至少保留 1 小时

        Duration expireDuration = Duration.ofMillis(ttlMillis);
        userRecordUtil.markAsParticipated(flashId, userId, expireDuration);

        log.info("用户 {} 抢购成功，秒杀活动 ID: {}, 商品 ID: {}, 数量: {}", userId, flashId, productId, quantity);

        // 6. 事务提交后发送延迟消息（关键：移出事务）
        sendDelayMessageAfterCommit(record, flashId, quantity);

        return true;
    }

    /**
     * 在事务提交后发送延迟消息
     */
    private void sendDelayMessageAfterCommit(BizFlashSaleRecord record, String flashId, int quantity) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                RecordMessage msg = new RecordMessage();
                msg.setRecordId(record.getId());
                msg.setFlashId(flashId);
                msg.setUserId(record.getUserId());
                msg.setProductId(record.getProductId());

                long expireDelayMs = 5 * 60 * 1000; // 5分钟
                MessagePostProcessor processor = message -> {
                    message.getMessageProperties().setHeader("x-delay", expireDelayMs);
                    return message;
                };

                try {
                    rabbitTemplate.convertAndSend(
                            FlashSaleRabbitConfig.DELAYED_EXCHANGE,
                            FlashSaleRabbitConfig.DELAYED_ROUTING_KEY,
                            msg,
                            processor
                    );
                } catch (Exception ex) {
                    log.error("延迟消息发送失败，recordId: {}", record.getId(), ex);
                    // 可选：记录到本地消息表做补偿
                }
            }
        });
    }

    @Override
    public void markAsOrdered(String recordId, String orderId) {
        this.update(new LambdaUpdateWrapper<BizFlashSaleRecord>()
                .eq(BizFlashSaleRecord::getId, recordId)
                .set(BizFlashSaleRecord::getStatus, FlashSaleRecordStatus.ORDERED)
                .set(BizFlashSaleRecord::getOrderId, orderId)
        );
    }

    @Override
    public boolean hasValidRecord(String userId, String flashId) {
        return this.exists(new LambdaQueryWrapper<BizFlashSaleRecord>()
                .eq(BizFlashSaleRecord::getUserId, userId)
                .eq(BizFlashSaleRecord::getFlashId, flashId));
    }

}