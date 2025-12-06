package io.jiangbyte.app.biz.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author CharlieZhang
 * @version v1.0
 * @date 03/12/2025
 * @description TODO
 */
@Data
public class CardReq {
    @NotNull
    @Min(1)
    private Integer quantity; // 数量

    @NotBlank
    private String productId; // 商品ID

    private String skuId; // SKU ID
    private String flashId; // 秒杀ID

    @NotBlank
    private String specId; // 规格ID
}
