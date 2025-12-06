package io.jiangbyte.app.biz.orderitem.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.biz.orderitem.dto.BizOrderItemDto;
import io.jiangbyte.app.biz.orderitem.dto.BizOrderItemPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.biz.orderitem.service.BizOrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 订单明细表 控制器
*/
@Tag(name = "订单明细表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class BizOrderItemController {
    private final BizOrderItemService bizOrderItemService;

    @Operation(summary = "获取订单明细分页")
    @SaCheckPermission("/biz/order/item/page")
    @GetMapping("/biz/order/item/page")
    public Result<?> page(@ParameterObject BizOrderItemPageQuery req) {
        return Result.success(bizOrderItemService.page(req));
    }

    @Operation(summary = "添加订单明细")
    @SaCheckPermission("/biz/order/item/add")
    @PostMapping("/biz/order/item/add")
    public Result<?> add(@RequestBody @Valid BizOrderItemDto req) {
        bizOrderItemService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑订单明细")
    @SaCheckPermission("/biz/order/item/edit")
    @PostMapping("/biz/order/item/edit")
    public Result<?> edit(@RequestBody @Valid BizOrderItemDto req) {
        bizOrderItemService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除订单明细")
    @SaCheckPermission("/biz/order/item/delete")
    @PostMapping("/biz/order/item/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        bizOrderItemService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取订单明细详情")
    @SaCheckPermission("/biz/order/item/detail")
    @GetMapping("/biz/order/item/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(bizOrderItemService.detail(id));
    }

    @Operation(summary = "获取订单明细N最新")
    @SaCheckPermission("/biz/order/item/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizOrderItemService.latest(n));
    }

    @Operation(summary = "获取订单明细TopN")
    @SaCheckPermission("/biz/order/item/top")
    @GetMapping("/biz/order/item/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizOrderItemService.topN(n));
    }

}