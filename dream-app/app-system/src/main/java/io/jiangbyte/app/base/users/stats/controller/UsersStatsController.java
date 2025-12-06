package io.jiangbyte.app.base.users.stats.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.base.users.stats.dto.UsersStatsDto;
import io.jiangbyte.app.base.users.stats.dto.UsersStatsPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.base.users.stats.service.UsersStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户统计信息表 控制器
*/
@Tag(name = "用户统计信息表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class UsersStatsController {
    private final UsersStatsService usersStatsService;

    @Operation(summary = "获取用户统计信息分页")
    @SaCheckPermission("/users/stats/page")
    @GetMapping("/users/stats/page")
    public Result<?> page(@ParameterObject UsersStatsPageQuery req) {
        return Result.success(usersStatsService.page(req));
    }

    @Operation(summary = "添加用户统计信息")
    @SaCheckPermission("/users/stats/add")
    @PostMapping("/users/stats/add")
    public Result<?> add(@RequestBody @Valid UsersStatsDto req) {
        usersStatsService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑用户统计信息")
    @SaCheckPermission("/users/stats/edit")
    @PostMapping("/users/stats/edit")
    public Result<?> edit(@RequestBody @Valid UsersStatsDto req) {
        usersStatsService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除用户统计信息")
    @SaCheckPermission("/users/stats/delete")
    @PostMapping("/users/stats/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        usersStatsService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取用户统计信息详情")
    @SaCheckPermission("/users/stats/detail")
    @GetMapping("/users/stats/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(usersStatsService.detail(id));
    }

    @Operation(summary = "获取用户统计信息N最新")
    @SaCheckPermission("/users/stats/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(usersStatsService.latest(n));
    }

    @Operation(summary = "获取用户统计信息TopN")
    @SaCheckPermission("/users/stats/top")
    @GetMapping("/users/stats/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(usersStatsService.topN(n));
    }

}