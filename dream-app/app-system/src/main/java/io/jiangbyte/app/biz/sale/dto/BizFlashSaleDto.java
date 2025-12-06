package io.jiangbyte.app.biz.sale.dto;

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
* @description 限时抢购活动 编辑参数
*/
@Data
@Schema(name = "BizFlashSale", description = "限时抢购活动 编辑参数")
public class BizFlashSaleDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "抢购活动ID")
    private String id;

    @Schema(description = "关联商品ID")
    private String productId;

    @Schema(description = "原价")
    private BigDecimal originalPrice;

    @Schema(description = "抢购价")
    private BigDecimal discountPrice;

    @Schema(description = "折扣描述")
    private String discount;

    @Schema(description = "每人限购数量")
    private Integer limitPerUser;

    @Schema(description = "促销标签")
    private String tags;

    @Schema(description = "现有库存")
    private Integer totalStock;

    @Schema(description = "是否上架")
    private Boolean status;

    @Schema(description = "抢购开始时间")
    private Date startTime;

    @Schema(description = "抢购结束时间")
    private Date endTime;

}