package io.jiangbyte.app.base.system.log.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.base.system.log.dto.SysLogDto;
import io.jiangbyte.app.base.system.log.dto.SysLogPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.base.system.log.service.SysLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 系统活动日志记录表 控制器
*/
@Tag(name = "系统活动日志记录表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class SysLogController {
    private final SysLogService sysLogService;

    @Operation(summary = "获取系统活动日志记录分页")
    @SaCheckPermission("/sys/log/page")
    @GetMapping("/sys/log/page")
    public Result<?> page(@ParameterObject SysLogPageQuery req) {
        return Result.success(sysLogService.page(req));
    }

    @Operation(summary = "添加系统活动日志记录")
    @SaCheckPermission("/sys/log/add")
    @PostMapping("/sys/log/add")
    public Result<?> add(@RequestBody @Valid SysLogDto req) {
        sysLogService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑系统活动日志记录")
    @SaCheckPermission("/sys/log/edit")
    @PostMapping("/sys/log/edit")
    public Result<?> edit(@RequestBody @Valid SysLogDto req) {
        sysLogService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除系统活动日志记录")
    @SaCheckPermission("/sys/log/delete")
    @PostMapping("/sys/log/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        sysLogService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取系统活动日志记录详情")
    @SaCheckPermission("/sys/log/detail")
    @GetMapping("/sys/log/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(sysLogService.detail(id));
    }

    @Operation(summary = "获取系统活动日志记录N最新")
    @SaCheckPermission("/sys/log/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(sysLogService.latest(n));
    }

    @Operation(summary = "获取系统活动日志记录TopN")
    @SaCheckPermission("/sys/log/top")
    @GetMapping("/sys/log/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(sysLogService.topN(n));
    }

}