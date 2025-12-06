package io.jiangbyte.app.biz.product.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @description 商品主 编辑参数
 */
@Data
@Schema(name = "BizProduct", description = "商品主 编辑参数")
public class BizProductDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "商品ID")
    private String id;

    @Schema(description = "商品标题")
    private String title;

    @Schema(description = "封面图URL")
    private String cover;

    @Schema(description = "商品轮播图URL列表")
    private List<String> images;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "分类")
    private String categoryId;

    @Schema(description = "产地")
    private String origin;

    @Schema(description = "商品描述")
    private String description;

    @Schema(description = "商品详情列表")
    private List<String> details;

    @Schema(description = "服务保障列表")
    private List<String> service;

    @Schema(description = "是否上架")
    private Boolean status;

}