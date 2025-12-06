package io.jiangbyte.app.base.config.item.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jiangbyte.app.base.config.item.dto.WebsiteConfigInfo;
import io.jiangbyte.app.base.config.item.entity.ConfigItem;
import io.jiangbyte.app.base.config.item.dto.ConfigItemDto;
import io.jiangbyte.app.base.config.item.dto.ConfigItemPageQuery;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 系统配置表 服务类
*/
public interface ConfigItemService extends IService<ConfigItem> {
    Page<ConfigItem> page(ConfigItemPageQuery req);

    void add(ConfigItemDto req);

    void edit(ConfigItemDto req);

    void delete(List<String> ids);

    ConfigItem detail(String id);

    List<ConfigItem> latest(int n);

    List<ConfigItem> topN(int n);

    WebsiteConfigInfo websiteConfig();
}