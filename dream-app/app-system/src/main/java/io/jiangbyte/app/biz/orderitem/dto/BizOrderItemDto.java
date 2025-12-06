package io.jiangbyte.app.biz.orderitem.dto;

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

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 订单明细 编辑参数
*/
@Data
@Schema(name = "BizOrderItem", description = "订单明细 编辑参数")
public class BizOrderItemDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "订单ID")
    private String orderId;

    @Schema(description = "商品ID")
    private String productId;

    @Schema(description = "SKU ID")
    private String skuId;

    @Schema(description = "限时抢购ID")
    private String flashId;

    @Schema(description = "商品标题（快照）")
    private String productTitle;

    @Schema(description = "商品封面（快照）")
    private String cover;

    @Schema(description = "规格快照（如颜色、尺寸）")
    private String specs;

    @Schema(description = "下单时原价")
    private BigDecimal originalPrice;

    @Schema(description = "下单时实际单价")
    private BigDecimal discountPrice;

    @Schema(description = "购买数量")
    private Integer quantity;

    @Schema(description = "小计金额 = 单价 × 数量")
    private BigDecimal totalAmount;

}