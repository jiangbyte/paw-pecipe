package io.jiangbyte.app.base.auths.role.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.base.auths.role.dto.AuthsRoleMenuDto;
import io.jiangbyte.app.base.auths.role.dto.AuthsRoleMenuPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.base.auths.role.service.AuthsRoleMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 角色菜单关联表 控制器
*/
@Tag(name = "角色菜单关联表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class AuthsRoleMenuController {
    private final AuthsRoleMenuService authsRoleMenuService;

    @Operation(summary = "获取角色菜单关联分页")
    @SaCheckPermission("/auths/role/menu/page")
    @GetMapping("/auths/role/menu/page")
    public Result<?> page(@ParameterObject AuthsRoleMenuPageQuery req) {
        return Result.success(authsRoleMenuService.page(req));
    }

    @Operation(summary = "添加角色菜单关联")
    @SaCheckPermission("/auths/role/menu/add")
    @PostMapping("/auths/role/menu/add")
    public Result<?> add(@RequestBody @Valid AuthsRoleMenuDto req) {
        authsRoleMenuService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑角色菜单关联")
    @SaCheckPermission("/auths/role/menu/edit")
    @PostMapping("/auths/role/menu/edit")
    public Result<?> edit(@RequestBody @Valid AuthsRoleMenuDto req) {
        authsRoleMenuService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除角色菜单关联")
    @SaCheckPermission("/auths/role/menu/delete")
    @PostMapping("/auths/role/menu/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        authsRoleMenuService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取角色菜单关联详情")
    @SaCheckPermission("/auths/role/menu/detail")
    @GetMapping("/auths/role/menu/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(authsRoleMenuService.detail(id));
    }

    @Operation(summary = "获取角色菜单关联N最新")
    @SaCheckPermission("/auths/role/menu/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(authsRoleMenuService.latest(n));
    }

    @Operation(summary = "获取角色菜单关联TopN")
    @SaCheckPermission("/auths/role/menu/top")
    @GetMapping("/auths/role/menu/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(authsRoleMenuService.topN(n));
    }

}