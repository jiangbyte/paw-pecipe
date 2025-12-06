package io.jiangbyte.app.biz.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.biz.order.entity.BizOrder;
import io.jiangbyte.app.biz.order.dto.BizOrderDto;
import io.jiangbyte.app.biz.order.dto.BizOrderPageQuery;
import io.jiangbyte.app.biz.order.mapper.BizOrderMapper;
import io.jiangbyte.app.biz.order.service.BizOrderService;
import io.jiangbyte.framework.utils.SortUtils;
import io.jiangbyte.framework.enums.ISortOrderEnum;
import io.jiangbyte.framework.exception.BusinessException;
import io.jiangbyte.framework.pojo.BasePageRequest;
import io.jiangbyte.framework.result.ResultCode;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-06-23
* @description 订单主表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class BizOrderServiceImpl extends ServiceImpl<BizOrderMapper, BizOrder> implements BizOrderService {

    @Override
    public Page<BizOrder> page(BizOrderPageQuery req) {
        QueryWrapper<BizOrder> queryWrapper = new QueryWrapper<BizOrder>().checkSqlInjection();
        SortUtils.handleSort(BizOrder.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizOrderDto req) {
        BizOrder bean = BeanUtil.toBean(req, BizOrder.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizOrderDto req) {
        if (!this.exists(new LambdaQueryWrapper<BizOrder>().eq(BizOrder::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        BizOrder bean = BeanUtil.toBean(req, BizOrder.class);
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
    public BizOrder detail(String id) {
        BizOrder bizOrder = this.getById(id);
        if (ObjectUtil.isEmpty(bizOrder)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return bizOrder;
    }

    @Override
    public List<BizOrder> latest(int n) {
        return this.list(new QueryWrapper<BizOrder>()
            .lambda()
            .orderByDesc(BizOrder::getId)
            .last("limit " + n));
    }

    @Override
    public List<BizOrder> topN(int n) {
        return this.list(new QueryWrapper<BizOrder>()
            .lambda()
            .orderByDesc(BizOrder::getId)
            .last("limit " + n));
    }

}