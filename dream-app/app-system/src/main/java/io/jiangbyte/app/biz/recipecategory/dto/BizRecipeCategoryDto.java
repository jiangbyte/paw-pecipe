package io.jiangbyte.app.biz.recipecategory.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 菜谱分类 编辑参数
*/
@Data
@Schema(name = "BizCategory", description = "菜谱分类 编辑参数")
public class BizRecipeCategoryDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "菜谱分类")
    private String name;

}