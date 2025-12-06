package io.jiangbyte.app.base.users.stats.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.base.users.stats.entity.UsersStats;
import io.jiangbyte.app.base.users.stats.dto.UsersStatsDto;
import io.jiangbyte.app.base.users.stats.dto.UsersStatsPageQuery;
import io.jiangbyte.app.base.users.stats.mapper.UsersStatsMapper;
import io.jiangbyte.app.base.users.stats.service.UsersStatsService;
import io.jiangbyte.framework.utils.SortUtils;
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
* @description 用户统计信息表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class UsersStatsServiceImpl extends ServiceImpl<UsersStatsMapper, UsersStats> implements UsersStatsService {

    @Override
    public Page<UsersStats> page(UsersStatsPageQuery req) {
        QueryWrapper<UsersStats> queryWrapper = new QueryWrapper<UsersStats>().checkSqlInjection();
        SortUtils.handleSort(UsersStats.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(UsersStatsDto req) {
        UsersStats bean = BeanUtil.toBean(req, UsersStats.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(UsersStatsDto req) {
        if (!this.exists(new LambdaQueryWrapper<UsersStats>().eq(UsersStats::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        UsersStats bean = BeanUtil.toBean(req, UsersStats.class);
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
    public UsersStats detail(String id) {
        UsersStats usersStats = this.getById(id);
        if (ObjectUtil.isEmpty(usersStats)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return usersStats;
    }

    @Override
    public List<UsersStats> latest(int n) {
        return this.list(new QueryWrapper<UsersStats>()
            .lambda()
            .orderByDesc(UsersStats::getId)
            .last("limit " + n));
    }

    @Override
    public List<UsersStats> topN(int n) {
        return this.list(new QueryWrapper<UsersStats>()
            .lambda()
            .orderByDesc(UsersStats::getId)
            .last("limit " + n));
    }

}