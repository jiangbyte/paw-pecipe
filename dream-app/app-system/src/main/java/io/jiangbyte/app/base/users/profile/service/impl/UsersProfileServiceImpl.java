package io.jiangbyte.app.base.users.profile.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.base.users.profile.entity.UsersProfile;
import io.jiangbyte.app.base.users.profile.dto.UsersProfileDto;
import io.jiangbyte.app.base.users.profile.dto.UsersProfilePageQuery;
import io.jiangbyte.app.base.users.profile.mapper.UsersProfileMapper;
import io.jiangbyte.app.base.users.profile.service.UsersProfileService;
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
* @description 用户档案详情表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class UsersProfileServiceImpl extends ServiceImpl<UsersProfileMapper, UsersProfile> implements UsersProfileService {

    @Override
    public Page<UsersProfile> page(UsersProfilePageQuery req) {
        QueryWrapper<UsersProfile> queryWrapper = new QueryWrapper<UsersProfile>().checkSqlInjection();
        SortUtils.handleSort(UsersProfile.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(UsersProfileDto req) {
        UsersProfile bean = BeanUtil.toBean(req, UsersProfile.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(UsersProfileDto req) {
        if (!this.exists(new LambdaQueryWrapper<UsersProfile>().eq(UsersProfile::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        UsersProfile bean = BeanUtil.toBean(req, UsersProfile.class);
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
    public UsersProfile detail(String id) {
        UsersProfile usersProfile = this.getById(id);
        if (ObjectUtil.isEmpty(usersProfile)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return usersProfile;
    }

    @Override
    public List<UsersProfile> latest(int n) {
        return this.list(new QueryWrapper<UsersProfile>()
            .lambda()
            .orderByDesc(UsersProfile::getId)
            .last("limit " + n));
    }

    @Override
    public List<UsersProfile> topN(int n) {
        return this.list(new QueryWrapper<UsersProfile>()
            .lambda()
            .orderByDesc(UsersProfile::getId)
            .last("limit " + n));
    }

}