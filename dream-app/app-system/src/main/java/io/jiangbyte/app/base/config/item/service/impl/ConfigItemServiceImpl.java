package io.jiangbyte.app.base.config.item.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jiangbyte.app.base.config.item.dto.WebsiteConfigInfo;
import io.jiangbyte.app.base.config.item.entity.ConfigItem;
import io.jiangbyte.app.base.config.item.dto.ConfigItemDto;
import io.jiangbyte.app.base.config.item.dto.ConfigItemPageQuery;
import io.jiangbyte.app.base.config.item.mapper.ConfigItemMapper;
import io.jiangbyte.app.base.config.item.service.ConfigItemService;
import io.jiangbyte.framework.utils.SortUtils;
import io.jiangbyte.framework.exception.BusinessException;
import io.jiangbyte.framework.pojo.BasePageRequest;
import io.jiangbyte.framework.result.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-06-23
* @description 系统配置表 服务实现类
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class ConfigItemServiceImpl extends ServiceImpl<ConfigItemMapper, ConfigItem> implements ConfigItemService {

    @Override
    public Page<ConfigItem> page(ConfigItemPageQuery req) {
        QueryWrapper<ConfigItem> queryWrapper = new QueryWrapper<ConfigItem>().checkSqlInjection();
        if (ObjectUtil.isNotEmpty(req.getKeyword())) {
            queryWrapper.lambda().like(ConfigItem::getName, req.getKeyword());
        }
        SortUtils.handleSort(ConfigItem.class, queryWrapper, req.getSortField(), req.getSortOrder());
        return this.page(BasePageRequest.Page(
                        Optional.ofNullable(req.getCurrent()).orElse(1),
                        Optional.ofNullable(req.getPageSize()).orElse(10)),
                queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ConfigItemDto req) {
        ConfigItem bean = BeanUtil.toBean(req, ConfigItem.class);
        bean.setId(null);
        this.save(bean);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ConfigItemDto req) {
        if (!this.exists(new LambdaQueryWrapper<ConfigItem>().eq(ConfigItem::getId, req.getId()))) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        ConfigItem bean = BeanUtil.toBean(req, ConfigItem.class);
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
    public ConfigItem detail(String id) {
        ConfigItem configItem = this.getById(id);
        if (ObjectUtil.isEmpty(configItem)) {
            throw new BusinessException(ResultCode.PARAM_ERROR);
        }
        return configItem;
    }

    @Override
    public List<ConfigItem> latest(int n) {
        return this.list(new QueryWrapper<ConfigItem>()
            .lambda()
            .orderByDesc(ConfigItem::getId)
            .last("limit " + n));
    }

    @Override
    public List<ConfigItem> topN(int n) {
        return this.list(new QueryWrapper<ConfigItem>()
            .lambda()
            .orderByDesc(ConfigItem::getId)
            .last("limit " + n));
    }

    @Override
    public WebsiteConfigInfo websiteConfig() {
        List<ConfigItem> configItems = this.list(new QueryWrapper<ConfigItem>()
                .lambda()
                .eq(ConfigItem::getGroupCode, "WEBSITE")
        );

        if (configItems == null || configItems.isEmpty()) {
            return null;
        }

        // 创建配置信息对象
        WebsiteConfigInfo configInfo = new WebsiteConfigInfo();

        // 将配置项映射到对象属性
        for (ConfigItem item : configItems) {
            setConfigValue(configInfo, item.getCode(), item.getValue());
        }

        return configInfo;
    }

    /**
     * 根据配置项代码设置对应的属性值
     */
    private void setConfigValue(WebsiteConfigInfo configInfo, String code, String value) {
        if (value == null) return;

        switch (code) {
            // 网站信息
            case "WEBSITE_NAME":
                configInfo.setWebsiteName(value);
                break;
            case "WEBSITE_LOGO":
                configInfo.setWebsiteLogo(value);
                break;
            case "WEBSITE_DESCRIPTION":
                configInfo.setWebsiteDescription(value);
                break;
            case "WEBSITE_KEYWORDS":
                configInfo.setWebsiteKeywords(value);
                break;
            case "WEBSITE_AUTHOR":
                configInfo.setWebsiteAuthor(value);
                break;
            case "WEBSITE_COPYRIGHT":
                configInfo.setWebsiteCopyright(value);
                break;
            case "WEBSITE_VERSION":
                configInfo.setWebsiteVersion(value);
                break;

            // 联系信息
            case "CONTACT_QQ":
                configInfo.setContactQQ(value);
                break;
            case "CONTACT_EMAIL":
                configInfo.setContactEmail(value);
                break;
            case "CONTACT_WECHAT":
                configInfo.setContactWeChat(value);
                break;

            // 社交链接（JSON格式）
            case "SOCIAL_LINKS":
                if (StringUtils.isNotBlank(value)) {
                    try {
                        List<WebsiteConfigInfo.SocialLink> socialLinks = JSON.parseArray(value, WebsiteConfigInfo.SocialLink.class);
                        configInfo.setSocialLinks(socialLinks);
                    } catch (Exception e) {
                        log.warn("解析社交链接JSON失败: {}", value, e);
                    }
                }
                break;
        }
    }
}