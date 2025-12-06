package io.jiangbyte.app.biz.recipecategory.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.app.biz.recipecategory.dto.BizRecipeCategoryDto;
import io.jiangbyte.app.biz.recipecategory.dto.BizRecipeCategoryPageQuery;
import io.jiangbyte.app.biz.recipecategory.service.BizRecipeCategoryService;
import io.jiangbyte.framework.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 菜谱分类表 控制器
*/
@Tag(name = "菜谱分类表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class BizRecipeCategoryController {
    private final BizRecipeCategoryService bizCategoryService;

    @Operation(summary = "获取菜谱分类分页")
    @SaCheckPermission("/biz/recipe/category/page")
    @GetMapping("/biz/recipe/category/page")
    public Result<?> page(@ParameterObject BizRecipeCategoryPageQuery req) {
        return Result.success(bizCategoryService.page(req));
    }

    @Operation(summary = "添加菜谱分类")
    @SaCheckPermission("/biz/recipe/category/add")
    @PostMapping("/biz/recipe/category/add")
    public Result<?> add(@RequestBody @Valid BizRecipeCategoryDto req) {
        bizCategoryService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑菜谱分类")
    @SaCheckPermission("/biz/recipe/category/edit")
    @PostMapping("/biz/recipe/category/edit")
    public Result<?> edit(@RequestBody @Valid BizRecipeCategoryDto req) {
        bizCategoryService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除菜谱分类")
    @SaCheckPermission("/biz/recipe/category/delete")
    @PostMapping("/biz/recipe/category/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        bizCategoryService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取菜谱分类详情")
    @SaCheckPermission("/biz/recipe/category/detail")
    @GetMapping("/biz/recipe/category/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(bizCategoryService.detail(id));
    }

    @Operation(summary = "获取菜谱分类N最新")
    @SaCheckPermission("/biz/recipe/category/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizCategoryService.latest(n));
    }

    @Operation(summary = "获取菜谱分类TopN")
    @SaCheckPermission("/biz/recipe/category/top")
    @GetMapping("/biz/recipe/category/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizCategoryService.topN(n));
    }

    @Operation(summary = "获取菜谱分类列表")
    @SaCheckPermission("/biz/recipe/category/lists")
    @GetMapping("/biz/recipe/category/lists")
    public Result<?> lists() {
        return Result.success(bizCategoryService.lists());
    }
}