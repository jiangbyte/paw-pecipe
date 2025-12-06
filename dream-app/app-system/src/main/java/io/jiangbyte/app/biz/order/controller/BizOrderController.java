package io.jiangbyte.app.biz.order.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.biz.order.dto.BizOrderDto;
import io.jiangbyte.app.biz.order.dto.BizOrderPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.biz.order.service.BizOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 订单主表 控制器
*/
@Tag(name = "订单主表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class BizOrderController {
    private final BizOrderService bizOrderService;

    @Operation(summary = "获取订单主分页")
    @SaCheckPermission("/biz/order/page")
    @GetMapping("/biz/order/page")
    public Result<?> page(@ParameterObject BizOrderPageQuery req) {
        return Result.success(bizOrderService.page(req));
    }

    @Operation(summary = "添加订单主")
    @SaCheckPermission("/biz/order/add")
    @PostMapping("/biz/order/add")
    public Result<?> add(@RequestBody @Valid BizOrderDto req) {
        bizOrderService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑订单主")
    @SaCheckPermission("/biz/order/edit")
    @PostMapping("/biz/order/edit")
    public Result<?> edit(@RequestBody @Valid BizOrderDto req) {
        bizOrderService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除订单主")
    @SaCheckPermission("/biz/order/delete")
    @PostMapping("/biz/order/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        bizOrderService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取订单主详情")
    @SaCheckPermission("/biz/order/detail")
    @GetMapping("/biz/order/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(bizOrderService.detail(id));
    }

    @Operation(summary = "获取订单主N最新")
    @SaCheckPermission("/biz/order/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizOrderService.latest(n));
    }

    @Operation(summary = "获取订单主TopN")
    @SaCheckPermission("/biz/order/top")
    @GetMapping("/biz/order/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizOrderService.topN(n));
    }

}