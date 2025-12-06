package io.jiangbyte.app.biz.flashsalerecord.dto;

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
* @description 秒杀资格记录 编辑参数
*/
@Data
@Schema(name = "BizFlashSaleRecord", description = "秒杀资格记录 编辑参数")
public class BizFlashSaleRecordDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    private String id;

    @Schema(description = "秒杀活动ID")
    private String flashId;

    @Schema(description = "用户ID")
    private String userId;

    private String productId;

    private Integer quantity;

    @Schema(description = "0=已抢到未下单, 1=已下单, 2=已支付, 3=已失效")
    private Boolean status;

    @Schema(description = "关联订单ID（下单后填充）")
    private String orderId;

    @Schema(description = "资格过期时间（如5分钟内必须下单）")
    private Date expiredAt;

}