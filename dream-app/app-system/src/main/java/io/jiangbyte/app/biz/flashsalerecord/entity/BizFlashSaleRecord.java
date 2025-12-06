package io.jiangbyte.app.biz.flashsalerecord.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableName;
import io.jiangbyte.framework.pojo.BaseEntity;
import java.io.Serializable;
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
* @description 秒杀资格记录表
*/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "biz_flash_sale_record", autoResultMap = true)
@Schema(name = "BizFlashSaleRecord", description = "秒杀资格记录表")
public class BizFlashSaleRecord extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @SortConfig(type = SortType.NUMERIC_STRING)
    private String id;

    @Schema(description = "秒杀活动ID")
    private String flashId;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "")
    private String productId;

    @Schema(description = "")
    private Integer quantity;

    @Schema(description = "0=已抢到未下单, 1=已下单, 2=已支付, 3=已失效")
    private Integer status;

    @Schema(description = "关联订单ID（下单后填充）")
    private String orderId;

    @Schema(description = "资格过期时间（如5分钟内必须下单）")
    private Date expiredAt;
}
