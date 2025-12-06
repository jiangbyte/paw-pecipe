package io.jiangbyte.app.biz.cart.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.jiangbyte.framework.pojo.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.util.Date;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 购物车 编辑参数
*/
@Data
@Schema(name = "BizCart", description = "购物车 编辑参数")
public class BizCartDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "商品ID")
    private String productId;

    @Schema(description = "SKU ID（可为空，用于普通商品）")
    private String skuId;

    @Schema(description = "限时抢购活动ID（若来自抢购）")
    private String flashId;

    @Schema(description = "数量")
    private Integer quantity;

    @Schema(description = "是否选中（用于结算）")
    private Boolean selected;

    private String specId; // 规格ID

}