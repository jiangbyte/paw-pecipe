package io.jiangbyte.app.biz.specs.dto;

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
* @description 规格 编辑参数
*/
@Data
@Schema(name = "BizProductSpecs", description = "规格 编辑参数")
public class BizProductSpecsDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "抢购活动ID")
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