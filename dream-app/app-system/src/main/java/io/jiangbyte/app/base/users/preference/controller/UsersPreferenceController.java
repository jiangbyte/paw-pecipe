package io.jiangbyte.app.base.users.preference.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.base.users.preference.dto.UsersPreferenceDto;
import io.jiangbyte.app.base.users.preference.dto.UsersPreferencePageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.base.users.preference.service.UsersPreferenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户偏好设置表 控制器
*/
@Tag(name = "用户偏好设置表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class UsersPreferenceController {
    private final UsersPreferenceService usersPreferenceService;

    @Operation(summary = "获取用户偏好设置分页")
    @SaCheckPermission("/users/preference/page")
    @GetMapping("/users/preference/page")
    public Result<?> page(@ParameterObject UsersPreferencePageQuery req) {
        return Result.success(usersPreferenceService.page(req));
    }

    @Operation(summary = "添加用户偏好设置")
    @SaCheckPermission("/users/preference/add")
    @PostMapping("/users/preference/add")
    public Result<?> add(@RequestBody @Valid UsersPreferenceDto req) {
        usersPreferenceService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑用户偏好设置")
    @SaCheckPermission("/users/preference/edit")
    @PostMapping("/users/preference/edit")
    public Result<?> edit(@RequestBody @Valid UsersPreferenceDto req) {
        usersPreferenceService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除用户偏好设置")
    @SaCheckPermission("/users/preference/delete")
    @PostMapping("/users/preference/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        usersPreferenceService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取用户偏好设置详情")
    @SaCheckPermission("/users/preference/detail")
    @GetMapping("/users/preference/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(usersPreferenceService.detail(id));
    }

    @Operation(summary = "获取用户偏好设置N最新")
    @SaCheckPermission("/users/preference/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(usersPreferenceService.latest(n));
    }

    @Operation(summary = "获取用户偏好设置TopN")
    @SaCheckPermission("/users/preference/top")
    @GetMapping("/users/preference/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(usersPreferenceService.topN(n));
    }

}