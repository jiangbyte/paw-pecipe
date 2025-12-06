package ${package.ServiceImpl};

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${package.Entity}.${entity};
import ${package.Dto}.${entity}Dto;
import ${package.PageQuery}.${entity}PageQuery;
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${entity}Service;
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
* @description ${table.comment!} 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class ${entity}ServiceImpl extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${entity}Service {

    @Override
    public Page<${entity}> page(${entity}PageQuery req) {
        QueryWrapper<${entity}> queryWrapper = new QueryWrapper<${entity}>().checkSqlInjection();
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if ["title"]?seq_contains(field.propertyName)>
        if (ObjectUtil.isNotEmpty(req.getKeyword())) {
            queryWrapper.lambda().like(${entity}::getTitle, req.getKeyword());
        }
    <#elseif ["name"]?seq_contains(field.propertyName)>
        if (ObjectUtil.isNotEmpty(req.getKeyword())) {
            queryWrapper.lambda().like(${entity}::getName, req.getKeyword());
        }
    </#if>
</#list>
<#------------  END 字段循环遍历  ---------->
        SortUtils.handleSort(${entity}.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(${entity}Dto req) {
        ${entity} bean = BeanUtil.toBean(req, ${entity}.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(${entity}Dto req) {
        if (!this.exists(new LambdaQueryWrapper<${entity}>().eq(${entity}::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        ${entity} bean = BeanUtil.toBean(req, ${entity}.class);
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
    public ${entity} detail(String id) {
        ${entity} ${table.entityPath} = this.getById(id);
        if (ObjectUtil.isEmpty(${table.entityPath})) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return ${table.entityPath};
    }

    @Override
    public List<${entity}> latest(int n) {
        return this.list(new QueryWrapper<${entity}>()
            .lambda()
            .orderByDesc(${entity}::getId)
            .last("limit " + n));
    }

    @Override
    public List<${entity}> topN(int n) {
        return this.list(new QueryWrapper<${entity}>()
            .lambda()
            .orderByDesc(${entity}::getId)
            .last("limit " + n));
    }

}