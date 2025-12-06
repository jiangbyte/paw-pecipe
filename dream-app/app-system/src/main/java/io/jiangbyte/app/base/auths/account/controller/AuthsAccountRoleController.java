package io.jiangbyte.app.base.auths.account.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountRoleDto;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountRolePageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.base.auths.account.service.AuthsAccountRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 账户角色关联表 控制器
*/
@Tag(name = "账户角色关联表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class AuthsAccountRoleController {
    private final AuthsAccountRoleService authsAccountRoleService;

    @Operation(summary = "获取账户角色关联分页")
    @SaCheckPermission("/auths/account/role/page")
    @GetMapping("/auths/account/role/page")
    public Result<?> page(@ParameterObject AuthsAccountRolePageQuery req) {
        return Result.success(authsAccountRoleService.page(req));
    }

    @Operation(summary = "添加账户角色关联")
    @SaCheckPermission("/auths/account/role/add")
    @PostMapping("/auths/account/role/add")
    public Result<?> add(@RequestBody @Valid AuthsAccountRoleDto req) {
        authsAccountRoleService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑账户角色关联")
    @SaCheckPermission("/auths/account/role/edit")
    @PostMapping("/auths/account/role/edit")
    public Result<?> edit(@RequestBody @Valid AuthsAccountRoleDto req) {
        authsAccountRoleService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除账户角色关联")
    @SaCheckPermission("/auths/account/role/delete")
    @PostMapping("/auths/account/role/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        authsAccountRoleService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取账户角色关联详情")
    @SaCheckPermission("/auths/account/role/detail")
    @GetMapping("/auths/account/role/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(authsAccountRoleService.detail(id));
    }

    @Operation(summary = "获取账户角色关联N最新")
    @SaCheckPermission("/auths/account/role/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(authsAccountRoleService.latest(n));
    }

    @Operation(summary = "获取账户角色关联TopN")
    @SaCheckPermission("/auths/account/role/top")
    @GetMapping("/auths/account/role/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(authsAccountRoleService.topN(n));
    }

}