package io.jiangbyte.app.biz.order.dto;

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
* @description 订单主 编辑参数
*/
@Data
@Schema(name = "BizOrder", description = "订单主 编辑参数")
public class BizOrderDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "订单ID")
    private String id;

    @Schema(description = "订单编号（唯一，如 ORD20251203123456）")
    private String orderNo;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "订单总金额（含运费）")
    private BigDecimal totalAmount;

    @Schema(description = "实付金额（优惠后）")
    private BigDecimal actualAmount;

    @Schema(description = "优惠金额")
    private BigDecimal discountAmount;

    @Schema(description = "运费")
    private BigDecimal freightAmount;

    @Schema(description = "订单状态：0=待支付，1=已支付，2=已发货，3=已完成，4=已取消，5=已退款")
    private Byte status;

    @Schema(description = "支付方式：wechat, alipay, balance 等")
    private String payType;

    @Schema(description = "支付时间")
    private Date payTime;

    @Schema(description = "收货人姓名")
    private String receiverName;

    @Schema(description = "收货人电话")
    private String receiverPhone;

    @Schema(description = "详细地址")
    private String receiverAddress;

    @Schema(description = "省")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String district;

    @Schema(description = "用户备注")
    private String remark;

    @Schema(description = "支付超时分钟数")
    private Integer timeoutMinutes;

    @Schema(description = "订单过期时间（用于自动取消）")
    private Date expiredAt;

}