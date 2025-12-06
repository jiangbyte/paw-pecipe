package io.jiangbyte.app.base.system.log.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.base.system.log.entity.SysLog;
import io.jiangbyte.app.base.system.log.dto.SysLogDto;
import io.jiangbyte.app.base.system.log.dto.SysLogPageQuery;
import io.jiangbyte.app.base.system.log.mapper.SysLogMapper;
import io.jiangbyte.app.base.system.log.service.SysLogService;
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
* @description 系统活动日志记录表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Override
    public Page<SysLog> page(SysLogPageQuery req) {
        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<SysLog>().checkSqlInjection();
        SortUtils.handleSort(SysLog.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SysLogDto req) {
        SysLog bean = BeanUtil.toBean(req, SysLog.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SysLogDto req) {
        if (!this.exists(new LambdaQueryWrapper<SysLog>().eq(SysLog::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        SysLog bean = BeanUtil.toBean(req, SysLog.class);
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
    public SysLog detail(String id) {
        SysLog sysLog = this.getById(id);
        if (ObjectUtil.isEmpty(sysLog)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return sysLog;
    }

    @Override
    public List<SysLog> latest(int n) {
        return this.list(new QueryWrapper<SysLog>()
            .lambda()
            .orderByDesc(SysLog::getId)
            .last("limit " + n));
    }

    @Override
    public List<SysLog> topN(int n) {
        return this.list(new QueryWrapper<SysLog>()
            .lambda()
            .orderByDesc(SysLog::getId)
            .last("limit " + n));
    }

}