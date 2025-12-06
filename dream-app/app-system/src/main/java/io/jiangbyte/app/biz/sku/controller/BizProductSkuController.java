package io.jiangbyte.app.biz.sku.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.biz.sku.dto.BizProductSkuDto;
import io.jiangbyte.app.biz.sku.dto.BizProductSkuPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.biz.sku.service.BizProductSkuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 商品日常销售SKU表 控制器
*/
@Tag(name = "商品日常销售SKU表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class BizProductSkuController {
    private final BizProductSkuService bizProductSkuService;

    @Operation(summary = "获取商品日常销售SKU分页")
    @SaCheckPermission("/biz/product/sku/page")
    @GetMapping("/biz/product/sku/page")
    public Result<?> page(@ParameterObject BizProductSkuPageQuery req) {
        return Result.success(bizProductSkuService.page(req));
    }

    @Operation(summary = "获取商品日常销售SKU分页")
    @GetMapping("/biz/product/sku/page_with_product")
    public Result<?> pageWithProduct(@ParameterObject BizProductSkuPageQuery req) {
        return Result.success(bizProductSkuService.pageWithProduct(req));
    }

    @Operation(summary = "添加商品日常销售SKU")
    @SaCheckPermission("/biz/product/sku/add")
    @PostMapping("/biz/product/sku/add")
    public Result<?> add(@RequestBody @Valid BizProductSkuDto req) {
        bizProductSkuService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑商品日常销售SKU")
    @SaCheckPermission("/biz/product/sku/edit")
    @PostMapping("/biz/product/sku/edit")
    public Result<?> edit(@RequestBody @Valid BizProductSkuDto req) {
        bizProductSkuService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除商品日常销售SKU")
    @SaCheckPermission("/biz/product/sku/delete")
    @PostMapping("/biz/product/sku/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        bizProductSkuService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取商品日常销售SKU详情")
    @SaCheckPermission("/biz/product/sku/detail")
    @GetMapping("/biz/product/sku/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(bizProductSkuService.detail(id));
    }

    @Operation(summary = "获取商品日常销售SKU详情")
    @GetMapping("/biz/product/sku/detail_with_product/{id}")
    public Result<?> detailWithProduct(@PathVariable("id") String id) {
        return Result.success(bizProductSkuService.detailWithProduct(id));
    }

    @Operation(summary = "获取商品日常销售SKUN最新")
    @SaCheckPermission("/biz/product/sku/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizProductSkuService.latest(n));
    }

    @Operation(summary = "获取商品日常销售SKUTopN")
    @SaCheckPermission("/biz/product/sku/top")
    @GetMapping("/biz/product/sku/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizProductSkuService.topN(n));
    }

}