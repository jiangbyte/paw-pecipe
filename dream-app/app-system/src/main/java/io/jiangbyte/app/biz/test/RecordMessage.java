package io.jiangbyte.app.biz.test;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author CharlieZhang
 * @version v1.0
 * @date 05/12/2025
 * @description TODO
 */
@Data
public class RecordMessage {
    private String recordId;
    private String userId;
    @Schema(description = "秒杀活动ID")
    private String flashId;
    @Schema(description = "")
    private Integer quantity;
    @Schema(description = "")
    private String productId;

}
