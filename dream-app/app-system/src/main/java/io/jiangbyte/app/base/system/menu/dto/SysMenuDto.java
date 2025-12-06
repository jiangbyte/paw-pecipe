package io.jiangbyte.app.base.system.menu.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 菜单 编辑参数
*/
@Data
@Schema(name = "SysMenu", description = "菜单 编辑参数")
public class SysMenuDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "父级ID")
    private String pid;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "菜单路径")
    private String path;

    @Schema(description = "组件路径")
    private String componentPath;

    @Schema(description = "重定向路径")
    private String redirect;

    @Schema(description = "外部链接地址")
    private String externalUrl;

    @Schema(description = "菜单类型：0-内部菜单 1-外链菜单 2-重定向菜单 3-iframe嵌入")
    private Integer menuType;

    @Schema(description = "打开方式：0-当前窗口 1-新窗口打开")
    private Integer openTarget;

    @Schema(description = "iframe属性")
    private String iframeAttrs;

    @Schema(description = "菜单标题")
    private String title;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "缓存")
    private Boolean keepAlive;

    @Schema(description = "可见")
    private Boolean visible;

    @Schema(description = "钉钉")
    private Boolean pined;

    @Schema(description = "无标签页")
    private Boolean withoutTab;

    @Schema(description = "头部参数")
    private String parameters;

    @Schema(description = "路由参数")
    private String extraParams;

}