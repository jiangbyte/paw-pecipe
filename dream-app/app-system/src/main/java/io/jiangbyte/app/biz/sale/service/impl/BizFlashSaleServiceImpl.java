package io.jiangbyte.app.biz.sale.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.biz.sale.entity.BizFlashSale;
import io.jiangbyte.app.biz.sale.dto.BizFlashSaleDto;
import io.jiangbyte.app.biz.sale.dto.BizFlashSalePageQuery;
import io.jiangbyte.app.biz.sale.mapper.BizFlashSaleMapper;
import io.jiangbyte.app.biz.sale.service.BizFlashSaleService;
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
* @description 限时抢购活动表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class BizFlashSaleServiceImpl extends ServiceImpl<BizFlashSaleMapper, BizFlashSale> implements BizFlashSaleService {

    @Override
    public Page<BizFlashSale> page(BizFlashSalePageQuery req) {
        QueryWrapper<BizFlashSale> queryWrapper = new QueryWrapper<BizFlashSale>().checkSqlInjection();
        SortUtils.handleSort(BizFlashSale.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BizFlashSaleDto req) {
        BizFlashSale bean = BeanUtil.toBean(req, BizFlashSale.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(BizFlashSaleDto req) {
        if (!this.exists(new LambdaQueryWrapper<BizFlashSale>().eq(BizFlashSale::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        BizFlashSale bean = BeanUtil.toBean(req, BizFlashSale.class);
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
    public BizFlashSale detail(String id) {
        BizFlashSale bizFlashSale = this.getById(id);
        if (ObjectUtil.isEmpty(bizFlashSale)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return bizFlashSale;
    }

    @Override
    public List<BizFlashSale> latest(int n) {
        return this.list(new QueryWrapper<BizFlashSale>()
            .lambda()
            .orderByDesc(BizFlashSale::getId)
            .last("limit " + n));
    }

    @Override
    public List<BizFlashSale> topN(int n) {
        return this.list(new QueryWrapper<BizFlashSale>()
            .lambda()
            .orderByDesc(BizFlashSale::getId)
            .last("limit " + n));
    }

}