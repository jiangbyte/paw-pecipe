package io.jiangbyte.app.base.dashboard.controller;

import io.jiangbyte.app.base.dashboard.service.DashboardService;
import io.jiangbyte.framework.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZhangJiangHu
 * @version v1.0
 * @date 18/11/2025
 * @description TODO
 */
@Tag(name = "Dashboard 控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class DashboardController {
    private final DashboardService dashboardService;

    @Operation(summary = "获取仪表盘数据")
    @GetMapping("/dashboard/pane/items")
    public Result<?> getDashboardPaneItems() {
        return Result.success(dashboardService.getDashboardPaneItems());
    }

}
