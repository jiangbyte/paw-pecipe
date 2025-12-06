package io.jiangbyte.app.base.users.preference.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.base.users.preference.entity.UsersPreference;
import io.jiangbyte.app.base.users.preference.dto.UsersPreferenceDto;
import io.jiangbyte.app.base.users.preference.dto.UsersPreferencePageQuery;
import io.jiangbyte.app.base.users.preference.mapper.UsersPreferenceMapper;
import io.jiangbyte.app.base.users.preference.service.UsersPreferenceService;
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
* @description 用户偏好设置表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class UsersPreferenceServiceImpl extends ServiceImpl<UsersPreferenceMapper, UsersPreference> implements UsersPreferenceService {

    @Override
    public Page<UsersPreference> page(UsersPreferencePageQuery req) {
        QueryWrapper<UsersPreference> queryWrapper = new QueryWrapper<UsersPreference>().checkSqlInjection();
        SortUtils.handleSort(UsersPreference.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(UsersPreferenceDto req) {
        UsersPreference bean = BeanUtil.toBean(req, UsersPreference.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(UsersPreferenceDto req) {
        if (!this.exists(new LambdaQueryWrapper<UsersPreference>().eq(UsersPreference::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        UsersPreference bean = BeanUtil.toBean(req, UsersPreference.class);
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
    public UsersPreference detail(String id) {
        UsersPreference usersPreference = this.getById(id);
        if (ObjectUtil.isEmpty(usersPreference)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return usersPreference;
    }

    @Override
    public List<UsersPreference> latest(int n) {
        return this.list(new QueryWrapper<UsersPreference>()
            .lambda()
            .orderByDesc(UsersPreference::getId)
            .last("limit " + n));
    }

    @Override
    public List<UsersPreference> topN(int n) {
        return this.list(new QueryWrapper<UsersPreference>()
            .lambda()
            .orderByDesc(UsersPreference::getId)
            .last("limit " + n));
    }

}