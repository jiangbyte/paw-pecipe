package io.jiangbyte.app.base.auths.account.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountDto;
import io.jiangbyte.app.base.auths.account.dto.AuthsAccountPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.base.auths.account.service.AuthsAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 核心账户表 控制器
*/
@Tag(name = "核心账户表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class AuthsAccountController {
    private final AuthsAccountService authsAccountService;

    @Operation(summary = "获取核心账户分页")
    @SaCheckPermission("/auths/account/page")
    @GetMapping("/auths/account/page")
    public Result<?> page(@ParameterObject AuthsAccountPageQuery req) {
        return Result.success(authsAccountService.page(req));
    }

    @Operation(summary = "添加核心账户")
    @SaCheckPermission("/auths/account/add")
    @PostMapping("/auths/account/add")
    public Result<?> add(@RequestBody @Valid AuthsAccountDto req) {
        authsAccountService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑核心账户")
    @SaCheckPermission("/auths/account/edit")
    @PostMapping("/auths/account/edit")
    public Result<?> edit(@RequestBody @Valid AuthsAccountDto req) {
        authsAccountService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除核心账户")
    @SaCheckPermission("/auths/account/delete")
    @PostMapping("/auths/account/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        authsAccountService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取核心账户详情")
    @SaCheckPermission("/auths/account/detail")
    @GetMapping("/auths/account/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(authsAccountService.detail(id));
    }

    @Operation(summary = "获取核心账户N最新")
    @SaCheckPermission("/auths/account/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(authsAccountService.latest(n));
    }

    @Operation(summary = "获取核心账户TopN")
    @SaCheckPermission("/auths/account/top")
    @GetMapping("/auths/account/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(authsAccountService.topN(n));
    }

    @Operation(summary = "获取用户账户")
    @GetMapping("/auths/account/user/{id}")
    public Result<?> userAccount(@PathVariable("id") String id) {
        return Result.success(authsAccountService.userAccount(id));
    }
}