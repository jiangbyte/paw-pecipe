package io.jiangbyte.app.biz.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.jiangbyte.app.biz.productcategory.entity.BizProductCategory;
import io.jiangbyte.app.biz.recipecategory.entity.BizRecipeCategory;
import io.jiangbyte.framework.pojo.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.io.Serial;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
 * @description 商品主表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "biz_product", autoResultMap = true)
@Schema(name = "BizProduct", description = "商品主表")
public class BizProduct extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "商品ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @SortConfig(type = SortType.NUMERIC_STRING)
    private String id;

    @Schema(description = "商品标题")
    private String title;

    @Schema(description = "封面图URL")
    private String cover;

    @Schema(description = "商品轮播图URL列表")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> images;

    @Schema(description = "品牌")
    private String brand;

    @Schema(description = "分类")
    @Trans(type = TransType.SIMPLE, target = BizProductCategory.class, fields = {"name"}, refs = {"categoryName"})
    private String categoryId;

    @Schema(description = "分类名称")
    @TableField(exist = false)
    private String categoryName;

    @Schema(description = "产地")
    private String origin;

    @Schema(description = "商品描述")
    private String description;

    @Schema(description = "商品详情列表")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> details;

    @Schema(description = "服务保障列表")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> service;

    @Schema(description = "是否上架")
    private Boolean status;
}
