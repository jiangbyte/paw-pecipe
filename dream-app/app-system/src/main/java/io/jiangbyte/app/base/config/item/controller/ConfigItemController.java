package io.jiangbyte.app.base.config.item.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.base.config.item.dto.ConfigItemDto;
import io.jiangbyte.app.base.config.item.dto.ConfigItemPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.base.config.item.service.ConfigItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 系统配置表 控制器
*/
@Tag(name = "系统配置表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class ConfigItemController {
    private final ConfigItemService configItemService;

    @Operation(summary = "获取系统配置分页")
    @SaCheckPermission("/config/item/page")
    @GetMapping("/config/item/page")
    public Result<?> page(@ParameterObject ConfigItemPageQuery req) {
        return Result.success(configItemService.page(req));
    }

    @Operation(summary = "添加系统配置")
    @SaCheckPermission("/config/item/add")
    @PostMapping("/config/item/add")
    public Result<?> add(@RequestBody @Valid ConfigItemDto req) {
        configItemService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑系统配置")
    @SaCheckPermission("/config/item/edit")
    @PostMapping("/config/item/edit")
    public Result<?> edit(@RequestBody @Valid ConfigItemDto req) {
        configItemService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除系统配置")
    @SaCheckPermission("/config/item/delete")
    @PostMapping("/config/item/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        configItemService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取系统配置详情")
    @SaCheckPermission("/config/item/detail")
    @GetMapping("/config/item/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(configItemService.detail(id));
    }

    @Operation(summary = "获取系统配置N最新")
    @SaCheckPermission("/config/item/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(configItemService.latest(n));
    }

    @Operation(summary = "获取系统配置TopN")
    @SaCheckPermission("/config/item/top")
    @GetMapping("/config/item/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(configItemService.topN(n));
    }

    @Operation(summary = "获取网站配置")
    @GetMapping("/config/item/website")
    public Result<?> websiteConfig() {
        return Result.success(configItemService.websiteConfig());
    }

}