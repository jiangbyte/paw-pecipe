package io.jiangbyte.app.base.config.item.entity;

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
* @description 系统配置表
*/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "config_item", autoResultMap = true)
@Schema(name = "ConfigItem", description = "系统配置表")
public class ConfigItem extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @SortConfig(type = SortType.NUMERIC_STRING)
    private String id;

    @Schema(description = "分组编码")
    private String groupCode;

    @Schema(description = "配置项名称")
    private String name;

    @Schema(description = "配置项代码")
    private String code;

    @Schema(description = "配置值")
    private String value;

    @Schema(description = "组件类型")
    private String componentType;

    @Schema(description = "配置描述")
    private String description;

    @Schema(description = "排序")
    @SortConfig(type = SortType.NUMERIC_STRING)
    private Integer sort;
}
