package io.jiangbyte.app.base.system.dict.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.base.system.dict.dto.SysDictDto;
import io.jiangbyte.app.base.system.dict.dto.SysDictPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.base.system.dict.service.SysDictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 系统字典表 控制器
*/
@Tag(name = "系统字典表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class SysDictController {
    private final SysDictService sysDictService;

    @Operation(summary = "获取系统字典分页")
    @SaCheckPermission("/sys/dict/page")
    @GetMapping("/sys/dict/page")
    public Result<?> page(@ParameterObject SysDictPageQuery req) {
        return Result.success(sysDictService.page(req));
    }

    @Operation(summary = "添加系统字典")
    @SaCheckPermission("/sys/dict/add")
    @PostMapping("/sys/dict/add")
    public Result<?> add(@RequestBody @Valid SysDictDto req) {
        sysDictService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑系统字典")
    @SaCheckPermission("/sys/dict/edit")
    @PostMapping("/sys/dict/edit")
    public Result<?> edit(@RequestBody @Valid SysDictDto req) {
        sysDictService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除系统字典")
    @SaCheckPermission("/sys/dict/delete")
    @PostMapping("/sys/dict/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        sysDictService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取系统字典详情")
    @SaCheckPermission("/sys/dict/detail")
    @GetMapping("/sys/dict/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(sysDictService.detail(id));
    }

    @Operation(summary = "获取系统字典N最新")
    @SaCheckPermission("/sys/dict/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(sysDictService.latest(n));
    }

    @Operation(summary = "获取系统字典TopN")
    @SaCheckPermission("/sys/dict/top")
    @GetMapping("/sys/dict/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(sysDictService.topN(n));
    }

    @Operation(summary = "获取系统字典树")
    @GetMapping("/sys/dict/tree/options")
    public Result<?> treeOptions(@RequestParam(value = "keyword", required = false) String keyword) {
        return Result.success(sysDictService.treeOptions(keyword));
    }

    @Operation(summary = "获取系统字典树")
    @GetMapping("/sys/dict/list/options")
    public Result<?> listOptions(@RequestParam(value = "keyword", required = false) String keyword) {
        return Result.success(sysDictService.listOptions(keyword));
    }

    @Operation(summary = "获取系统字典树")
    @GetMapping("/sys/dict/list/options/by/type")
    public Result<?> listOptionsByType(
            @RequestParam(value = "type") String type,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        return Result.success(sysDictService.listOptionsByType(type, keyword));
    }

    @Operation(summary = "获取系统字典树")
    @GetMapping("/sys/dict/list/type/options")
    public Result<?> listTypeOptions(
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        return Result.success(sysDictService.listTypeOptions(keyword));
    }
}