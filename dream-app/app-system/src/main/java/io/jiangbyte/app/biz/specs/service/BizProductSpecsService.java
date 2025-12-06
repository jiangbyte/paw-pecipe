package io.jiangbyte.app.biz.specs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.biz.specs.entity.BizProductSpecs;
import io.jiangbyte.app.biz.specs.dto.BizProductSpecsDto;
import io.jiangbyte.app.biz.specs.dto.BizProductSpecsPageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 规格表 服务类
*/
public interface BizProductSpecsService extends IService<BizProductSpecs> {
    Page<BizProductSpecs> page(BizProductSpecsPageQuery req);

    void add(BizProductSpecsDto req);

    void edit(BizProductSpecsDto req);

    void delete(List<String> ids);

    BizProductSpecs detail(String id);

    List<BizProductSpecs> latest(int n);

    List<BizProductSpecs> topN(int n);
}