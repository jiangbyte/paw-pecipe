package io.jiangbyte.app.biz.recipe.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.jiangbyte.framework.pojo.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.util.Date;
import java.util.List;

/**
 * @author Charlie Zhang
 * @version v1.0
 * @date 2025-12-03
 * @description 菜谱 编辑参数
 */
@Data
@Schema(name = "BizRecipe", description = "菜谱 编辑参数")
public class BizRecipeDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
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

    @Schema(description = "分类ID")
    private String categoryId;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "配料列表")
    private List<Ingredient> ingredients;

    @Schema(description = "步骤列表")
    private List<Step> steps;

    @Schema(description = "小贴士列表")
    private List<String> tips;

    private Boolean isPublic;

    @Schema(description = "分享量")
    private Integer share;

}