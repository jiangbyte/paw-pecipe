package io.jiangbyte.app.base.config.group.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.jiangbyte.framework.pojo.BaseEntity;

import java.io.Serial;

import io.jiangbyte.framework.enums.SortType;
import io.jiangbyte.framework.utils.SortConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 配置分组表
*/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "config_group", autoResultMap = true)
@Schema(name = "ConfigGroup", description = "配置分组表")
public class ConfigGroup extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @SortConfig(type = SortType.NUMERIC_STRING)
    private String id;

    @Schema(description = "分组名称")
    private String name;

    @Schema(description = "分组代码")
    private String code;

    @Schema(description = "分组描述")
    private String description;

    @Schema(description = "排序")
    @SortConfig(type = SortType.NUMERIC_STRING)
    private Integer sort;

    @Schema(description = "是否系统分组")
    private Boolean isSystem;
}
