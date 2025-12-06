package io.jiangbyte.app.base.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author ZhangJiangHu
 * @version v1.0
 * @date 26/11/2025
 * @description TODO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaneItem {
    private String icon;
    private String title;
    private BigDecimal value;
    private String unit;
}
