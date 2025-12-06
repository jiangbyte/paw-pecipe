package io.jiangbyte.app.base.config.group.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.base.config.group.entity.ConfigGroup;
import io.jiangbyte.app.base.config.group.dto.ConfigGroupDto;
import io.jiangbyte.app.base.config.group.dto.ConfigGroupPageQuery;
import io.jiangbyte.app.base.config.group.mapper.ConfigGroupMapper;
import io.jiangbyte.app.base.config.group.service.ConfigGroupService;
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
* @description 配置分组表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigGroupServiceImpl extends ServiceImpl<ConfigGroupMapper, ConfigGroup> implements ConfigGroupService {

    @Override
    public Page<ConfigGroup> page(ConfigGroupPageQuery req) {
        QueryWrapper<ConfigGroup> queryWrapper = new QueryWrapper<ConfigGroup>().checkSqlInjection();
        if (ObjectUtil.isNotEmpty(req.getKeyword())) {
            queryWrapper.lambda().like(ConfigGroup::getName, req.getKeyword());
        }
        SortUtils.handleSort(ConfigGroup.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ConfigGroupDto req) {
        ConfigGroup bean = BeanUtil.toBean(req, ConfigGroup.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ConfigGroupDto req) {
        if (!this.exists(new LambdaQueryWrapper<ConfigGroup>().eq(ConfigGroup::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        ConfigGroup bean = BeanUtil.toBean(req, ConfigGroup.class);
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
    public ConfigGroup detail(String id) {
        ConfigGroup configGroup = this.getById(id);
        if (ObjectUtil.isEmpty(configGroup)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return configGroup;
    }

    @Override
    public List<ConfigGroup> latest(int n) {
        return this.list(new QueryWrapper<ConfigGroup>()
            .lambda()
            .orderByDesc(ConfigGroup::getId)
            .last("limit " + n));
    }

    @Override
    public List<ConfigGroup> topN(int n) {
        return this.list(new QueryWrapper<ConfigGroup>()
            .lambda()
            .orderByDesc(ConfigGroup::getId)
            .last("limit " + n));
    }

}