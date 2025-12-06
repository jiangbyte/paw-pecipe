package io.jiangbyte.app.base.auths.role.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.base.auths.role.dto.AuthsRoleDto;
import io.jiangbyte.app.base.auths.role.dto.AuthsRolePageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.base.auths.role.service.AuthsRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 角色表 控制器
*/
@Tag(name = "角色表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class AuthsRoleController {
    private final AuthsRoleService authsRoleService;

    @Operation(summary = "获取角色分页")
    @SaCheckPermission("/auths/role/page")
    @GetMapping("/auths/role/page")
    public Result<?> page(@ParameterObject AuthsRolePageQuery req) {
        return Result.success(authsRoleService.page(req));
    }

    @Operation(summary = "添加角色")
    @SaCheckPermission("/auths/role/add")
    @PostMapping("/auths/role/add")
    public Result<?> add(@RequestBody @Valid AuthsRoleDto req) {
        authsRoleService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑角色")
    @SaCheckPermission("/auths/role/edit")
    @PostMapping("/auths/role/edit")
    public Result<?> edit(@RequestBody @Valid AuthsRoleDto req) {
        authsRoleService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除角色")
    @SaCheckPermission("/auths/role/delete")
    @PostMapping("/auths/role/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        authsRoleService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取角色详情")
    @SaCheckPermission("/auths/role/detail")
    @GetMapping("/auths/role/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(authsRoleService.detail(id));
    }

    @Operation(summary = "获取角色N最新")
    @SaCheckPermission("/auths/role/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(authsRoleService.latest(n));
    }

    @Operation(summary = "获取角色TopN")
    @SaCheckPermission("/auths/role/top")
    @GetMapping("/auths/role/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(authsRoleService.topN(n));
    }

}