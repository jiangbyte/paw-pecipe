package io.jiangbyte.app.base.config.group.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 配置分组 编辑参数
*/
@Data
@Schema(name = "ConfigGroup", description = "配置分组 编辑参数")
public class ConfigGroupDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "分组名称")
    private String name;

    @Schema(description = "分组代码")
    private String code;

    @Schema(description = "分组描述")
    private String description;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "是否系统分组")
    private Boolean isSystem;

}