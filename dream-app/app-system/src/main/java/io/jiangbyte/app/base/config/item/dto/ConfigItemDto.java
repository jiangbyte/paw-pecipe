package io.jiangbyte.app.base.config.item.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 系统配置 编辑参数
*/
@Data
@Schema(name = "ConfigItem", description = "系统配置 编辑参数")
public class ConfigItemDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
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
    private Integer sort;

}