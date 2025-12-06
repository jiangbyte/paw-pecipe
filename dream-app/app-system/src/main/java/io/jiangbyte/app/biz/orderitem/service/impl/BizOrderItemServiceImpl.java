package io.jiangbyte.app.biz.orderitem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.biz.orderitem.entity.BizOrderItem;
import io.jiangbyte.app.biz.orderitem.dto.BizOrderItemDto;
import io.jiangbyte.app.biz.orderitem.dto.BizOrderItemPageQuery;
import io.jiangbyte.app.biz.orderitem.mapper.BizOrderItemMapper;
import io.jiangbyte.app.biz.orderitem.service.BizOrderItemService;
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
* @description 订单明细表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class BizOrderItemServiceImpl extends ServiceImpl<BizOrderItemMapper, BizOrderItem> implements BizOrderItemService {

    @Override
    public Page<BizOrderItem> page(BizOrderItemPageQuery req) {
        QueryWrapper<BizOrderItem> queryWrapper = new QueryWrapper<BizOrderItem>().checkSqlInjection();
        SortUtils.handleSort(BizOrderItem.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizOrderItemDto req) {
        BizOrderItem bean = BeanUtil.toBean(req, BizOrderItem.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizOrderItemDto req) {
        if (!this.exists(new LambdaQueryWrapper<BizOrderItem>().eq(BizOrderItem::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        BizOrderItem bean = BeanUtil.toBean(req, BizOrderItem.class);
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
    public BizOrderItem detail(String id) {
        BizOrderItem bizOrderItem = this.getById(id);
        if (ObjectUtil.isEmpty(bizOrderItem)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return bizOrderItem;
    }

    @Override
    public List<BizOrderItem> latest(int n) {
        return this.list(new QueryWrapper<BizOrderItem>()
            .lambda()
            .orderByDesc(BizOrderItem::getId)
            .last("limit " + n));
    }

    @Override
    public List<BizOrderItem> topN(int n) {
        return this.list(new QueryWrapper<BizOrderItem>()
            .lambda()
            .orderByDesc(BizOrderItem::getId)
            .last("limit " + n));
    }

}