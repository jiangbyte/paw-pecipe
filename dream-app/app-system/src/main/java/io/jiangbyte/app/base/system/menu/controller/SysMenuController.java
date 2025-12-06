package io.jiangbyte.app.base.system.menu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.base.system.menu.dto.SysMenuDto;
import io.jiangbyte.app.base.system.menu.dto.SysMenuPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.base.system.menu.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 菜单表 控制器
*/
@Tag(name = "菜单表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class SysMenuController {
    private final SysMenuService sysMenuService;

    @Operation(summary = "获取菜单分页")
    @SaCheckPermission("/sys/menu/page")
    @GetMapping("/sys/menu/page")
    public Result<?> page(@ParameterObject SysMenuPageQuery req) {
        return Result.success(sysMenuService.page(req));
    }

    @Operation(summary = "添加菜单")
    @SaCheckPermission("/sys/menu/add")
    @PostMapping("/sys/menu/add")
    public Result<?> add(@RequestBody @Valid SysMenuDto req) {
        sysMenuService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑菜单")
    @SaCheckPermission("/sys/menu/edit")
    @PostMapping("/sys/menu/edit")
    public Result<?> edit(@RequestBody @Valid SysMenuDto req) {
        sysMenuService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除菜单")
    @SaCheckPermission("/sys/menu/delete")
    @PostMapping("/sys/menu/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        sysMenuService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取菜单详情")
    @SaCheckPermission("/sys/menu/detail")
    @GetMapping("/sys/menu/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(sysMenuService.detail(id));
    }

    @Operation(summary = "获取菜单N最新")
    @SaCheckPermission("/sys/menu/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(sysMenuService.latest(n));
    }

    @Operation(summary = "获取菜单TopN")
    @SaCheckPermission("/sys/menu/top")
    @GetMapping("/sys/menu/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(sysMenuService.topN(n));
    }

    @Operation(summary = "获取当前用户菜单")
    @SaCheckPermission("/sys/menu/list/tree")
    @GetMapping("/sys/menu/list/tree")
    public Result<?> getSysMenuListTreeWithAccountID() {
        String accountId = StpUtil.getLoginIdAsString();
        return Result.success(sysMenuService.getSysMenuListTreeWithAccountID(accountId));
    }

    @Operation(summary = "获取当前用户菜单")
    @SaCheckPermission("/sys/menu/list")
    @GetMapping("/sys/menu/list")
    public Result<?> getSysMenuListWithAccountID() {
        String accountId = StpUtil.getLoginIdAsString();
        return Result.success(sysMenuService.getSysMenuListWithAccountID(accountId));
    }

}