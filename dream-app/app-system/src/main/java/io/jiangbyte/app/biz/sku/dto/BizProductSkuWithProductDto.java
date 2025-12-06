package io.jiangbyte.app.biz.sku.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.jiangbyte.app.biz.product.dto.BizProductDto;
import io.jiangbyte.app.biz.product.entity.BizProduct;
import io.jiangbyte.app.biz.sku.entity.BizProductSku;
import io.jiangbyte.app.biz.specs.dto.BizProductSpecsDto;
import io.jiangbyte.app.biz.specs.entity.BizProductSpecs;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author CharlieZhang
 * @version v1.0
 * @date 03/12/2025
 * @description TODO
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BizProductSkuWithProductDto extends BizProductSku {
    private BizProduct product;
    private List<BizProductSpecs> specs;
}
