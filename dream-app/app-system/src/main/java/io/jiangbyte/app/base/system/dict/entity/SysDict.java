package io.jiangbyte.app.base.system.dict.entity;

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
* @description 系统字典表
*/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "sys_dict", autoResultMap = true)
@Schema(name = "SysDict", description = "系统字典表")
public class SysDict extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @SortConfig(type = SortType.NUMERIC_STRING)
    private String id;

    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "类型标签")
    private String typeLabel;

    @Schema(description = "字典值")
    private String dictValue;

    @Schema(description = "字典标签")
    private String dictLabel;

    @Schema(description = "排序号")
    @SortConfig(type = SortType.NUMERIC_STRING)
    private Integer sort;
}
