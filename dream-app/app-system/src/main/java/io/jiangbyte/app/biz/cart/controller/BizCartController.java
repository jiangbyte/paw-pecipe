package io.jiangbyte.app.biz.cart.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.app.biz.cart.dto.CardReq;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.biz.cart.dto.BizCartDto;
import io.jiangbyte.app.biz.cart.dto.BizCartPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.biz.cart.service.BizCartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 购物车表 控制器
*/
@Tag(name = "购物车表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class BizCartController {
    private final BizCartService bizCartService;

    @Operation(summary = "获取购物车分页")
    @SaCheckPermission("/biz/cart/page")
    @GetMapping("/biz/cart/page")
    public Result<?> page(@ParameterObject BizCartPageQuery req) {
        return Result.success(bizCartService.page(req));
    }

    @Operation(summary = "添加购物车")
    @SaCheckPermission("/biz/cart/add")
    @PostMapping("/biz/cart/add")
    public Result<?> add(@RequestBody @Valid BizCartDto req) {
        bizCartService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑购物车")
    @SaCheckPermission("/biz/cart/edit")
    @PostMapping("/biz/cart/edit")
    public Result<?> edit(@RequestBody @Valid BizCartDto req) {
        bizCartService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除购物车")
    @SaCheckPermission("/biz/cart/delete")
    @PostMapping("/biz/cart/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        bizCartService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取购物车详情")
    @SaCheckPermission("/biz/cart/detail")
    @GetMapping("/biz/cart/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(bizCartService.detail(id));
    }

    @Operation(summary = "获取购物车N最新")
    @SaCheckPermission("/biz/cart/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizCartService.latest(n));
    }

    @Operation(summary = "获取购物车TopN")
    @SaCheckPermission("/biz/cart/top")
    @GetMapping("/biz/cart/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizCartService.topN(n));
    }

    // addToCart
    @Operation(summary = "加入购物车")
    @PostMapping("/biz/cart/add_to_cart")
    public Result<?> addToCart(@RequestBody @Valid CardReq req) {
        bizCartService.addToCart(req);
        return Result.success();
    }
}