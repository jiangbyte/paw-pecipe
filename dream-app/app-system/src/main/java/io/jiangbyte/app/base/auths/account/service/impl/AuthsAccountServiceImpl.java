package io.jiangbyte.app.base.auths.account.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.base.auths.account.dto.UserAccount;
import io.jiangbyte.app.base.auths.account.entity.AuthsAccount;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountDto;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountPageQuery;
import io.jiangbyte.app.base.auths.account.mapper.AuthsAccountMapper;
import io.jiangbyte.app.base.auths.account.service.AuthsAccountService;
import io.jiangbyte.app.base.users.info.entity.UsersInfo;
import io.jiangbyte.app.base.users.info.mapper.UsersInfoMapper;
import io.jiangbyte.app.base.users.preference.entity.UsersPreference;
import io.jiangbyte.app.base.users.preference.mapper.UsersPreferenceMapper;
import io.jiangbyte.app.base.users.profile.entity.UsersProfile;
import io.jiangbyte.app.base.users.profile.mapper.UsersProfileMapper;
import io.jiangbyte.app.base.users.stats.entity.UsersStats;
import io.jiangbyte.app.base.users.stats.mapper.UsersStatsMapper;
import io.jiangbyte.framework.utils.SortUtils;
import io.jiangbyte.framework.exception.BusinessException;
import io.jiangbyte.framework.pojo.BasePageRequest;
import io.jiangbyte.framework.result.ResultCode;
import org.dromara.trans.service.impl.TransService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-06-23
* @description 核心账户表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthsAccountServiceImpl extends ServiceImpl<AuthsAccountMapper, AuthsAccount> implements AuthsAccountService {
    private final UsersInfoMapper usersInfoMapper;
    private final UsersPreferenceMapper usersPreferenceMapper;
    private final UsersProfileMapper usersProfileMapper;
    private final UsersStatsMapper usersStatsMapper;
    private final TransService transService;

    @Override
    public Page<AuthsAccount> page(AuthsAccountPageQuery req) {
        QueryWrapper<AuthsAccount> queryWrapper = new QueryWrapper<AuthsAccount>().checkSqlInjection();
        SortUtils.handleSort(AuthsAccount.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(AuthsAccountDto req) {
        AuthsAccount bean = BeanUtil.toBean(req, AuthsAccount.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(AuthsAccountDto req) {
        if (!this.exists(new LambdaQueryWrapper<AuthsAccount>().eq(AuthsAccount::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        AuthsAccount bean = BeanUtil.toBean(req, AuthsAccount.class);
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
    public AuthsAccount detail(String id) {
        AuthsAccount authsAccount = this.getById(id);
        if (ObjectUtil.isEmpty(authsAccount)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return authsAccount;
    }

    @Override
    public List<AuthsAccount> latest(int n) {
        return this.list(new QueryWrapper<AuthsAccount>()
            .lambda()
            .orderByDesc(AuthsAccount::getId)
            .last("limit " + n));
    }

    @Override
    public List<AuthsAccount> topN(int n) {
        return this.list(new QueryWrapper<AuthsAccount>()
            .lambda()
            .orderByDesc(AuthsAccount::getId)
            .last("limit " + n));
    }

    @Override
    public UserAccount userAccount(String id) {
        UserAccount userAccount = new UserAccount();
        AuthsAccount authsAccount = this.getById(id);
        if (ObjectUtil.isEmpty(authsAccount)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        transService.transOne(authsAccount);
        userAccount.setAccount(authsAccount);
        UsersInfo info = usersInfoMapper.selectOne(new LambdaQueryWrapper<UsersInfo>()
                .eq(UsersInfo::getAccountId, id)
        );
        transService.transOne(info);
        userAccount.setInfo(info);
        UsersPreference preference = usersPreferenceMapper.selectOne(new LambdaQueryWrapper<UsersPreference>()
                .eq(UsersPreference::getAccountId, id)
        );
        transService.transOne(preference);
        userAccount.setPreference(preference);
        UsersProfile profile = usersProfileMapper.selectOne(new LambdaQueryWrapper<UsersProfile>()
                .eq(UsersProfile::getAccountId, id)
        );
        transService.transOne(profile);
        userAccount.setProfile(profile);
        UsersStats stats = usersStatsMapper.selectOne(new LambdaQueryWrapper<UsersStats>()
                .eq(UsersStats::getAccountId, id)
        );
        transService.transOne(stats);
        userAccount.setStats(stats);
        return userAccount;
    }

}