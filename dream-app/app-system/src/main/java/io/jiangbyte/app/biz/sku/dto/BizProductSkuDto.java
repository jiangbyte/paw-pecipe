package io.jiangbyte.app.biz.sku.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.jiangbyte.framework.pojo.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
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
* @description 商品日常销售SKU 编辑参数
*/
@Data
@Schema(name = "BizProductSku", description = "商品日常销售SKU 编辑参数")
public class BizProductSkuDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "SKU ID")
    private String id;

    @Schema(description = "关联商品ID")
    private String productId;

    @Schema(description = "原价")
    private BigDecimal originalPrice;

    @Schema(description = "折扣价")
    private BigDecimal discountPrice;

    @Schema(description = "折扣描述")
    private String discount;

    @Schema(description = "每人限购数量")
    private Integer limitPerUser;

    @Schema(description = "促销标签")
    private List<String> tags;

    @Schema(description = "现有库存")
    private Integer totalStock;

    @Schema(description = "是否上架")
    private Boolean status;

}