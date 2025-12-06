package io.jiangbyte.app.base.auths.group.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.base.auths.group.dto.AuthsGroupDto;
import io.jiangbyte.app.base.auths.group.dto.AuthsGroupPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.base.auths.group.service.AuthsGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户组表 控制器
*/
@Tag(name = "用户组表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class AuthsGroupController {
    private final AuthsGroupService authsGroupService;

    @Operation(summary = "获取用户组分页")
    @SaCheckPermission("/auths/group/page")
    @GetMapping("/auths/group/page")
    public Result<?> page(@ParameterObject AuthsGroupPageQuery req) {
        return Result.success(authsGroupService.page(req));
    }

    @Operation(summary = "添加用户组")
    @SaCheckPermission("/auths/group/add")
    @PostMapping("/auths/group/add")
    public Result<?> add(@RequestBody @Valid AuthsGroupDto req) {
        authsGroupService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑用户组")
    @SaCheckPermission("/auths/group/edit")
    @PostMapping("/auths/group/edit")
    public Result<?> edit(@RequestBody @Valid AuthsGroupDto req) {
        authsGroupService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除用户组")
    @SaCheckPermission("/auths/group/delete")
    @PostMapping("/auths/group/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        authsGroupService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取用户组详情")
    @SaCheckPermission("/auths/group/detail")
    @GetMapping("/auths/group/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(authsGroupService.detail(id));
    }

    @Operation(summary = "获取用户组N最新")
    @SaCheckPermission("/auths/group/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(authsGroupService.latest(n));
    }

    @Operation(summary = "获取用户组TopN")
    @SaCheckPermission("/auths/group/top")
    @GetMapping("/auths/group/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(authsGroupService.topN(n));
    }

}