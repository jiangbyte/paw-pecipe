package io.jiangbyte.app.biz.recipe.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.app.biz.recipe.dto.BizRecipeDto;
import io.jiangbyte.app.biz.recipe.dto.BizRecipePageQuery;
import io.jiangbyte.app.biz.recipe.service.BizRecipeService;
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
* @description 菜谱表 控制器
*/
@Tag(name = "菜谱表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class BizRecipeController {
    private final BizRecipeService bizRecipeService;

    @Operation(summary = "获取菜谱分页")
    @SaCheckPermission("/biz/recipe/page")
    @GetMapping("/biz/recipe/page")
    public Result<?> page(@ParameterObject BizRecipePageQuery req) {
        return Result.success(bizRecipeService.page(req));
    }

    @Operation(summary = "添加菜谱")
    @SaCheckPermission("/biz/recipe/add")
    @PostMapping("/biz/recipe/add")
    public Result<?> add(@RequestBody @Valid BizRecipeDto req) {
        bizRecipeService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑菜谱")
    @SaCheckPermission("/biz/recipe/edit")
    @PostMapping("/biz/recipe/edit")
    public Result<?> edit(@RequestBody @Valid BizRecipeDto req) {
        bizRecipeService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除菜谱")
    @SaCheckPermission("/biz/recipe/delete")
    @PostMapping("/biz/recipe/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        bizRecipeService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取菜谱详情")
    @SaCheckPermission("/biz/recipe/detail")
    @GetMapping("/biz/recipe/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(bizRecipeService.detail(id));
    }

    @Operation(summary = "获取菜谱N最新")
    @SaCheckPermission("/biz/recipe/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizRecipeService.latest(n));
    }

    @Operation(summary = "获取菜谱TopN")
    @SaCheckPermission("/biz/recipe/top")
    @GetMapping("/biz/recipe/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizRecipeService.topN(n));
    }

}