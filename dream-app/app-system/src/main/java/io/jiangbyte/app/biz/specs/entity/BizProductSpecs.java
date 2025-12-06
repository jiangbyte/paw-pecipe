package io.jiangbyte.app.biz.specs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableName;
import io.jiangbyte.framework.pojo.BaseEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serial;
import java.util.Date;
import io.jiangbyte.framework.enums.SortType;
import io.jiangbyte.framework.utils.SortConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 规格表
*/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "biz_product_specs", autoResultMap = true)
@Schema(name = "BizProductSpecs", description = "规格表")
public class BizProductSpecs extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "抢购活动ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @SortConfig(type = SortType.NUMERIC_STRING)
    private String id;

    @Schema(description = "关联商品ID")
    private String productId;

    @Schema(description = "规格名称")
    private String name;

    @Schema(description = "规格价格")
    private BigDecimal price;

    @Schema(description = "规格库存")
    private Integer stock;

    @Schema(description = "是否上架")
    private Boolean status;
}
