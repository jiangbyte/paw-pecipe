package io.jiangbyte.app.biz.product.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.biz.product.dto.BizProductDto;
import io.jiangbyte.app.biz.product.dto.BizProductPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.biz.product.service.BizProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 商品主表 控制器
*/
@Tag(name = "商品主表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class BizProductController {
    private final BizProductService bizProductService;

    @Operation(summary = "获取商品主分页")
    @SaCheckPermission("/biz/product/page")
    @GetMapping("/biz/product/page")
    public Result<?> page(@ParameterObject BizProductPageQuery req) {
        return Result.success(bizProductService.page(req));
    }

    @Operation(summary = "添加商品主")
    @SaCheckPermission("/biz/product/add")
    @PostMapping("/biz/product/add")
    public Result<?> add(@RequestBody @Valid BizProductDto req) {
        bizProductService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑商品主")
    @SaCheckPermission("/biz/product/edit")
    @PostMapping("/biz/product/edit")
    public Result<?> edit(@RequestBody @Valid BizProductDto req) {
        bizProductService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除商品主")
    @SaCheckPermission("/biz/product/delete")
    @PostMapping("/biz/product/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        bizProductService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取商品主详情")
    @SaCheckPermission("/biz/product/detail")
    @GetMapping("/biz/product/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(bizProductService.detail(id));
    }

    @Operation(summary = "获取商品主N最新")
    @SaCheckPermission("/biz/product/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizProductService.latest(n));
    }

    @Operation(summary = "获取商品主TopN")
    @SaCheckPermission("/biz/product/top")
    @GetMapping("/biz/product/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizProductService.topN(n));
    }

}