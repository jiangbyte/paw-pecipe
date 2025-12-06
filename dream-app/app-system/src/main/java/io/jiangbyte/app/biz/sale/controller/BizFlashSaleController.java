package io.jiangbyte.app.biz.sale.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.biz.sale.dto.BizFlashSaleDto;
import io.jiangbyte.app.biz.sale.dto.BizFlashSalePageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.biz.sale.service.BizFlashSaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 限时抢购活动表 控制器
*/
@Tag(name = "限时抢购活动表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class BizFlashSaleController {
    private final BizFlashSaleService bizFlashSaleService;

    @Operation(summary = "获取限时抢购活动分页")
    @SaCheckPermission("/biz/flash/sale/page")
    @GetMapping("/biz/flash/sale/page")
    public Result<?> page(@ParameterObject BizFlashSalePageQuery req) {
        return Result.success(bizFlashSaleService.page(req));
    }

    @Operation(summary = "添加限时抢购活动")
    @SaCheckPermission("/biz/flash/sale/add")
    @PostMapping("/biz/flash/sale/add")
    public Result<?> add(@RequestBody @Valid BizFlashSaleDto req) {
        bizFlashSaleService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑限时抢购活动")
    @SaCheckPermission("/biz/flash/sale/edit")
    @PostMapping("/biz/flash/sale/edit")
    public Result<?> edit(@RequestBody @Valid BizFlashSaleDto req) {
        bizFlashSaleService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除限时抢购活动")
    @SaCheckPermission("/biz/flash/sale/delete")
    @PostMapping("/biz/flash/sale/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        bizFlashSaleService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取限时抢购活动详情")
    @SaCheckPermission("/biz/flash/sale/detail")
    @GetMapping("/biz/flash/sale/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(bizFlashSaleService.detail(id));
    }

    @Operation(summary = "获取限时抢购活动N最新")
    @SaCheckPermission("/biz/flash/sale/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizFlashSaleService.latest(n));
    }

    @Operation(summary = "获取限时抢购活动TopN")
    @SaCheckPermission("/biz/flash/sale/top")
    @GetMapping("/biz/flash/sale/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizFlashSaleService.topN(n));
    }

}