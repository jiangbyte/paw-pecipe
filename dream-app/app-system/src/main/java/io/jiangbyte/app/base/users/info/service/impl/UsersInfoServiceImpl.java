package io.jiangbyte.app.base.users.info.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.base.users.info.entity.UsersInfo;
import io.jiangbyte.app.base.users.info.dto.UsersInfoDto;
import io.jiangbyte.app.base.users.info.dto.UsersInfoPageQuery;
import io.jiangbyte.app.base.users.info.mapper.UsersInfoMapper;
import io.jiangbyte.app.base.users.info.service.UsersInfoService;
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
* @description 用户基本信息表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class UsersInfoServiceImpl extends ServiceImpl<UsersInfoMapper, UsersInfo> implements UsersInfoService {

    @Override
    public Page<UsersInfo> page(UsersInfoPageQuery req) {
        QueryWrapper<UsersInfo> queryWrapper = new QueryWrapper<UsersInfo>().checkSqlInjection();
        SortUtils.handleSort(UsersInfo.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(UsersInfoDto req) {
        UsersInfo bean = BeanUtil.toBean(req, UsersInfo.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(UsersInfoDto req) {
        if (!this.exists(new LambdaQueryWrapper<UsersInfo>().eq(UsersInfo::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        UsersInfo bean = BeanUtil.toBean(req, UsersInfo.class);
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
    public UsersInfo detail(String id) {
        UsersInfo usersInfo = this.getById(id);
        if (ObjectUtil.isEmpty(usersInfo)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return usersInfo;
    }

    @Override
    public List<UsersInfo> latest(int n) {
        return this.list(new QueryWrapper<UsersInfo>()
            .lambda()
            .orderByDesc(UsersInfo::getId)
            .last("limit " + n));
    }

    @Override
    public List<UsersInfo> topN(int n) {
        return this.list(new QueryWrapper<UsersInfo>()
            .lambda()
            .orderByDesc(UsersInfo::getId)
            .last("limit " + n));
    }

}