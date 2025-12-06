package io.jiangbyte.app.biz.specs.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.biz.specs.dto.BizProductSpecsDto;
import io.jiangbyte.app.biz.specs.dto.BizProductSpecsPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.biz.specs.service.BizProductSpecsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-12-03
* @description 规格表 控制器
*/
@Tag(name = "规格表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class BizProductSpecsController {
    private final BizProductSpecsService bizProductSpecsService;

    @Operation(summary = "获取规格分页")
    @SaCheckPermission("/biz/product/specs/page")
    @GetMapping("/biz/product/specs/page")
    public Result<?> page(@ParameterObject BizProductSpecsPageQuery req) {
        return Result.success(bizProductSpecsService.page(req));
    }

    @Operation(summary = "添加规格")
    @SaCheckPermission("/biz/product/specs/add")
    @PostMapping("/biz/product/specs/add")
    public Result<?> add(@RequestBody @Valid BizProductSpecsDto req) {
        bizProductSpecsService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑规格")
    @SaCheckPermission("/biz/product/specs/edit")
    @PostMapping("/biz/product/specs/edit")
    public Result<?> edit(@RequestBody @Valid BizProductSpecsDto req) {
        bizProductSpecsService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除规格")
    @SaCheckPermission("/biz/product/specs/delete")
    @PostMapping("/biz/product/specs/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        bizProductSpecsService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取规格详情")
    @SaCheckPermission("/biz/product/specs/detail")
    @GetMapping("/biz/product/specs/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(bizProductSpecsService.detail(id));
    }

    @Operation(summary = "获取规格N最新")
    @SaCheckPermission("/biz/product/specs/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizProductSpecsService.latest(n));
    }

    @Operation(summary = "获取规格TopN")
    @SaCheckPermission("/biz/product/specs/top")
    @GetMapping("/biz/product/specs/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizProductSpecsService.topN(n));
    }

}