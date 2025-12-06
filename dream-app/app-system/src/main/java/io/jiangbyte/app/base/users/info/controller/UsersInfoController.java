package io.jiangbyte.app.base.users.info.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.base.users.info.dto.UsersInfoDto;
import io.jiangbyte.app.base.users.info.dto.UsersInfoPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.base.users.info.service.UsersInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户基本信息表 控制器
*/
@Tag(name = "用户基本信息表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class UsersInfoController {
    private final UsersInfoService usersInfoService;

    @Operation(summary = "获取用户基本信息分页")
    @SaCheckPermission("/users/info/page")
    @GetMapping("/users/info/page")
    public Result<?> page(@ParameterObject UsersInfoPageQuery req) {
        return Result.success(usersInfoService.page(req));
    }

    @Operation(summary = "添加用户基本信息")
    @SaCheckPermission("/users/info/add")
    @PostMapping("/users/info/add")
    public Result<?> add(@RequestBody @Valid UsersInfoDto req) {
        usersInfoService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑用户基本信息")
    @SaCheckPermission("/users/info/edit")
    @PostMapping("/users/info/edit")
    public Result<?> edit(@RequestBody @Valid UsersInfoDto req) {
        usersInfoService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除用户基本信息")
    @SaCheckPermission("/users/info/delete")
    @PostMapping("/users/info/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        usersInfoService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取用户基本信息详情")
    @SaCheckPermission("/users/info/detail")
    @GetMapping("/users/info/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(usersInfoService.detail(id));
    }

    @Operation(summary = "获取用户基本信息N最新")
    @SaCheckPermission("/users/info/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(usersInfoService.latest(n));
    }

    @Operation(summary = "获取用户基本信息TopN")
    @SaCheckPermission("/users/info/top")
    @GetMapping("/users/info/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(usersInfoService.topN(n));
    }

}