package io.jiangbyte.app.biz.recipe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.jiangbyte.app.biz.recipecategory.entity.BizRecipeCategory;
import io.jiangbyte.app.biz.recipe.dto.Ingredient;
import io.jiangbyte.app.biz.recipe.dto.Step;
import io.jiangbyte.framework.pojo.BaseEntity;

import java.io.Serial;
import java.util.List;

import io.jiangbyte.framework.enums.SortType;
import io.jiangbyte.framework.utils.SortConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;

/**
 * @author Charlie Zhang
 * @version v1.0
 * @date 2025-12-03
 * @description 菜谱表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "biz_recipe", autoResultMap = true)
@Schema(name = "BizRecipe", description = "菜谱表")
public class BizRecipe extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @SortConfig(type = SortType.NUMERIC_STRING)
    private String id;

    @Schema(description = "识别ID")
    private String recognitionId;

    @Schema(description = "菜谱标题")
    private String title;

    @Schema(description = "封面图URL")
    private String cover;

    @Schema(description = "作者名")
    private String author;

    @Schema(description = "作者头像URL")
    private String avatar;

    @Schema(description = "制作时长")
    private Integer duration;

    @Schema(description = "难度等级")
    private String difficulty;

    @Schema(description = "点赞数")
    private Integer likes;

    @Schema(description = "收藏数")
    private Integer collects;

    @Schema(description = "浏览量")
    private Integer views;

    @Schema(description = "分享量")
    private Integer share;

    @Schema(description = "分类ID")
    @Trans(type = TransType.SIMPLE, target = BizRecipeCategory.class, fields = {"name"}, refs = {"categoryName"})
    private String categoryId;

    @Schema(description = "分类名称")
    @TableField(exist = false)
    private String categoryName;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "配料列表")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Ingredient> ingredients;

    @Schema(description = "步骤列表")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Step> steps;

    @Schema(description = "小贴士列表")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> tips;

    private Boolean isPublic;

}
