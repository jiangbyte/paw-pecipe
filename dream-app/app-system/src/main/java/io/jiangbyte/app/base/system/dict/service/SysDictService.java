package io.jiangbyte.app.base.system.dict.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.base.system.dict.entity.SysDict;
import io.jiangbyte.app.base.system.dict.dto.SysDictDto;
import io.jiangbyte.app.base.system.dict.dto.SysDictPageQuery;
import io.jiangbyte.framework.option.LabelOption;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 系统字典表 服务类
*/
public interface SysDictService extends IService<SysDict> {
    Page<SysDict> page(SysDictPageQuery req);

    void add(SysDictDto req);

    void edit(SysDictDto req);

    void delete(List<String> ids);

    SysDict detail(String id);

    List<SysDict> latest(int n);

    List<SysDict> topN(int n);

    List<LabelOption<String>> treeOptions(String keyword);

    List<LabelOption<String>> listOptions(String keyword);

    List<LabelOption<String>> listTypeOptions(String keyword);

    List<LabelOption<String>> listOptionsByType(String type, String keyword);
}