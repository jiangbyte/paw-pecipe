package io.jiangbyte.app.base.system.dict.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.base.system.dict.entity.SysDict;
import io.jiangbyte.app.base.system.dict.dto.SysDictDto;
import io.jiangbyte.app.base.system.dict.dto.SysDictPageQuery;
import io.jiangbyte.app.base.system.dict.mapper.SysDictMapper;
import io.jiangbyte.app.base.system.dict.service.SysDictService;
import io.jiangbyte.framework.option.LabelOption;
import io.jiangbyte.framework.utils.SortUtils;
import io.jiangbyte.framework.exception.BusinessException;
import io.jiangbyte.framework.pojo.BasePageRequest;
import io.jiangbyte.framework.result.ResultCode;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-06-23
* @description 系统字典表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Override
    public Page<SysDict> page(SysDictPageQuery req) {
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<SysDict>().checkSqlInjection();
        // 类型过滤
        if (ObjectUtil.isNotEmpty(req.getDictType())) {
            queryWrapper.lambda().eq(SysDict::getDictType, req.getDictType());
        } else {
            return new Page<>();
        }
        SortUtils.handleSort(SysDict.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(SysDictDto req) {
        SysDict bean = BeanUtil.toBean(req, SysDict.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(SysDictDto req) {
        if (!this.exists(new LambdaQueryWrapper<SysDict>().eq(SysDict::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        SysDict bean = BeanUtil.toBean(req, SysDict.class);
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
    public SysDict detail(String id) {
        SysDict sysDict = this.getById(id);
        if (ObjectUtil.isEmpty(sysDict)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return sysDict;
    }

    @Override
    public List<SysDict> latest(int n) {
        return this.list(new QueryWrapper<SysDict>()
            .lambda()
            .orderByDesc(SysDict::getId)
            .last("limit " + n));
    }

    @Override
    public List<SysDict> topN(int n) {
        return this.list(new QueryWrapper<SysDict>()
            .lambda()
            .orderByDesc(SysDict::getId)
            .last("limit " + n));
    }


    @Override
    public List<LabelOption<String>> treeOptions(String keyword) {
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<SysDict>().checkSqlInjection();
        // 关键字
        if (ObjectUtil.isNotEmpty(keyword)) {
            queryWrapper.lambda().like(SysDict::getTypeLabel, keyword);
        }

        // 从数据库获取的所有字典数据
        List<SysDict> dictList = this.list(queryWrapper);

        // 按字典类型分组
        Map<String, List<SysDict>> dictTypeMap = dictList.stream()
                .collect(Collectors.groupingBy(SysDict::getDictType));

        // 构建树形结构
        List<LabelOption<String>> tree = new ArrayList<>();

        // 遍历每种字典类型
        dictTypeMap.forEach((dictType, dictItems) -> {
            // 创建字典类型节点(父节点)
            LabelOption<String> typeNode = new LabelOption<>();
            typeNode.setText(dictItems.getFirst().getTypeLabel()); // 使用第一个元素的dictTypeLabel
            typeNode.setValue(dictType);

            // 为该类型下的字典项创建子节点
            List<LabelOption<String>> children = dictItems.stream()
                    .sorted(Comparator.comparing(SysDict::getSort)) // 按排序字段排序
                    .map(item -> {
                        LabelOption<String> child = new LabelOption<>();
                        child.setText(item.getDictLabel());
                        child.setValue(null);
                        return child;
                    })
                    .collect(Collectors.toList());

            typeNode.setChildren(children);
            tree.add(typeNode);
        });

        return tree;
    }

    @Override
    public List<LabelOption<String>> listOptions(String keyword) {
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<SysDict>().checkSqlInjection();
        // 关键字
        if (ObjectUtil.isNotEmpty(keyword)) {
            queryWrapper.lambda().like(SysDict::getTypeLabel, keyword);
        }
        // 从数据库获取的所有字典数据
        List<SysDict> dictList = this.list(queryWrapper);

        return new ArrayList<>(dictList.stream()
                .collect(Collectors.toMap(
                        SysDict::getDictType,  // 以dictType作为key
                        dict -> new LabelOption<>(dict.getDictType(), dict.getTypeLabel()),
                        (existing, replacement) -> existing  // 如果有重复key，保留已存在的
                ))
                .values());
    }

    @Override
    public List<LabelOption<String>> listTypeOptions(String keyword) {
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<SysDict>().checkSqlInjection();

        // 关键字
        if (ObjectUtil.isNotEmpty(keyword)) {
            queryWrapper.lambda().like(SysDict::getTypeLabel, keyword);
        }
        // 从数据库获取的所有字典数据
        List<SysDict> dictList = this.list(queryWrapper);

        return new ArrayList<>(dictList.stream()
                .collect(Collectors.toMap(
                        SysDict::getDictType,  // key
                        dict -> new LabelOption<>(dict.getDictType(), dict.getTypeLabel()),
                        (existing, replacement) -> existing  // 如果有重复key，保留已存在的
                ))
                .values());
    }

    @Override
    public List<LabelOption<String>> listOptionsByType(String type, String keyword) {
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<SysDict>().checkSqlInjection();
        queryWrapper.lambda().eq(SysDict::getDictType, type);
        if (ObjectUtil.isNotEmpty(keyword)) {
            queryWrapper.lambda().like(SysDict::getDictLabel, keyword);
        }
        return new ArrayList<>(this.list(queryWrapper).stream()
                .collect(Collectors.toMap(
                        SysDict::getDictValue,  // key
                        dict -> new LabelOption<>(dict.getDictValue(), dict.getDictLabel()),
                        (existing, replacement) -> existing  // 如果有重复key，保留已存在的
                ))
                .values());
    }
}