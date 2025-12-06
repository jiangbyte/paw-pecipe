package io.jiangbyte.app.base.users.profile.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.base.users.profile.dto.UsersProfileDto;
import io.jiangbyte.app.base.users.profile.dto.UsersProfilePageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.base.users.profile.service.UsersProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户档案详情表 控制器
*/
@Tag(name = "用户档案详情表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class UsersProfileController {
    private final UsersProfileService usersProfileService;

    @Operation(summary = "获取用户档案详情分页")
    @SaCheckPermission("/users/profile/page")
    @GetMapping("/users/profile/page")
    public Result<?> page(@ParameterObject UsersProfilePageQuery req) {
        return Result.success(usersProfileService.page(req));
    }

    @Operation(summary = "添加用户档案详情")
    @SaCheckPermission("/users/profile/add")
    @PostMapping("/users/profile/add")
    public Result<?> add(@RequestBody @Valid UsersProfileDto req) {
        usersProfileService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑用户档案详情")
    @SaCheckPermission("/users/profile/edit")
    @PostMapping("/users/profile/edit")
    public Result<?> edit(@RequestBody @Valid UsersProfileDto req) {
        usersProfileService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除用户档案详情")
    @SaCheckPermission("/users/profile/delete")
    @PostMapping("/users/profile/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        usersProfileService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取用户档案详情详情")
    @SaCheckPermission("/users/profile/detail")
    @GetMapping("/users/profile/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(usersProfileService.detail(id));
    }

    @Operation(summary = "获取用户档案详情N最新")
    @SaCheckPermission("/users/profile/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(usersProfileService.latest(n));
    }

    @Operation(summary = "获取用户档案详情TopN")
    @SaCheckPermission("/users/profile/top")
    @GetMapping("/users/profile/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(usersProfileService.topN(n));
    }

}