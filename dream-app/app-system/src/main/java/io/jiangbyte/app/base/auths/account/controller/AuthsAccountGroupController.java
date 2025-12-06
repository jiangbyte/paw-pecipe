package io.jiangbyte.app.base.auths.account.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountGroupDto;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountGroupPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.base.auths.account.service.AuthsAccountGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 账户用户组关联表 控制器
*/
@Tag(name = "账户用户组关联表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class AuthsAccountGroupController {
    private final AuthsAccountGroupService authsAccountGroupService;

    @Operation(summary = "获取账户用户组关联分页")
    @SaCheckPermission("/auths/account/group/page")
    @GetMapping("/auths/account/group/page")
    public Result<?> page(@ParameterObject AuthsAccountGroupPageQuery req) {
        return Result.success(authsAccountGroupService.page(req));
    }

    @Operation(summary = "添加账户用户组关联")
    @SaCheckPermission("/auths/account/group/add")
    @PostMapping("/auths/account/group/add")
    public Result<?> add(@RequestBody @Valid AuthsAccountGroupDto req) {
        authsAccountGroupService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑账户用户组关联")
    @SaCheckPermission("/auths/account/group/edit")
    @PostMapping("/auths/account/group/edit")
    public Result<?> edit(@RequestBody @Valid AuthsAccountGroupDto req) {
        authsAccountGroupService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除账户用户组关联")
    @SaCheckPermission("/auths/account/group/delete")
    @PostMapping("/auths/account/group/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        authsAccountGroupService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取账户用户组关联详情")
    @SaCheckPermission("/auths/account/group/detail")
    @GetMapping("/auths/account/group/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(authsAccountGroupService.detail(id));
    }

    @Operation(summary = "获取账户用户组关联N最新")
    @SaCheckPermission("/auths/account/group/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(authsAccountGroupService.latest(n));
    }

    @Operation(summary = "获取账户用户组关联TopN")
    @SaCheckPermission("/auths/account/group/top")
    @GetMapping("/auths/account/group/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(authsAccountGroupService.topN(n));
    }

}