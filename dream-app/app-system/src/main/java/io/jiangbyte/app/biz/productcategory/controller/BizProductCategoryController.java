package io.jiangbyte.app.biz.productcategory.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.biz.productcategory.dto.BizProductCategoryDto;
import io.jiangbyte.app.biz.productcategory.dto.BizProductCategoryPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.biz.productcategory.service.BizProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Charlie Zhang
 * @version v1.0
 * @date 2025-12-03
 * @description 商品分类表 控制器
 */
@Tag(name = "商品分类表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class BizProductCategoryController {
    private final BizProductCategoryService bizProductCategoryService;

    @Operation(summary = "获取商品分类分页")
    @SaCheckPermission("/biz/product/category/page")
    @GetMapping("/biz/product/category/page")
    public Result<?> page(@ParameterObject BizProductCategoryPageQuery req) {
        return Result.success(bizProductCategoryService.page(req));
    }

    @Operation(summary = "添加商品分类")
    @SaCheckPermission("/biz/product/category/add")
    @PostMapping("/biz/product/category/add")
    public Result<?> add(@RequestBody @Valid BizProductCategoryDto req) {
        bizProductCategoryService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑商品分类")
    @SaCheckPermission("/biz/product/category/edit")
    @PostMapping("/biz/product/category/edit")
    public Result<?> edit(@RequestBody @Valid BizProductCategoryDto req) {
        bizProductCategoryService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除商品分类")
    @SaCheckPermission("/biz/product/category/delete")
    @PostMapping("/biz/product/category/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        bizProductCategoryService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取商品分类详情")
    @SaCheckPermission("/biz/product/category/detail")
    @GetMapping("/biz/product/category/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(bizProductCategoryService.detail(id));
    }

    @Operation(summary = "获取商品分类N最新")
    @SaCheckPermission("/biz/product/category/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizProductCategoryService.latest(n));
    }

    @Operation(summary = "获取商品分类TopN")
    @SaCheckPermission("/biz/product/category/top")
    @GetMapping("/biz/product/category/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizProductCategoryService.topN(n));
    }

    @Operation(summary = "获取分类列表")
    @SaCheckPermission("/biz/product/category/lists")
    @GetMapping("/biz/product/category/lists")
    public Result<?> lists() {
        return Result.success(bizProductCategoryService.lists());
    }

}