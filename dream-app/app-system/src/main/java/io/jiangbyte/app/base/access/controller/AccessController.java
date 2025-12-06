package io.jiangbyte.app.base.access.controller;

import io.jiangbyte.app.base.access.dto.*;
import io.jiangbyte.app.base.access.service.AccessService;
import io.jiangbyte.framework.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author ZhangJiangHu
 * @version v1.0
 * @date 18/11/2025
 * @description TODO
 */
@Tag(name = "接入口控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class AccessController {
    private final AccessService accessService;

    @Operation(summary = "获取验证码")
    @GetMapping("/access/captcha")
    public Result<?> captcha() {
        return Result.success(accessService.captcha());
    }

    @Operation(summary = "登录")
    @PostMapping("/access/login")
    public Result<?> login(@RequestBody @Valid LoginReq loginReq) {
        return Result.success(accessService.doLogin(loginReq));
    }

    @Operation(summary = "注册")
    @PostMapping("/access/register")
    public Result<?>  register(@RequestBody @Valid RegisterReq registerReq) {
        return Result.success(accessService.doRegister(registerReq));
    }

    @Operation(summary = "登出")
    @PostMapping("/access/logout")
    public Result<?> logout() {
        accessService.doLogout();
        return Result.success();
    }

    @Operation(summary = "发起密码重置")
    @PostMapping("/access/password/reset")
    public Result<?> resetPassword(@RequestBody @Valid ResetPasswordReq resetPasswordReq) {
        return Result.success(accessService.doResetPassword(resetPasswordReq));
    }

    @Operation(summary = "验证密码重置Token")
    @GetMapping("/access/password/reset/token/validate")
    public Result<?> validateResetPasswordToken(@RequestParam @Valid String token) {
        return Result.success(accessService.validateResetPasswordToken(token));
    }

    @Operation(summary = "确认密码重置")
    @PostMapping("/access/password/reset/confirm")
    public Result<?> confirmResetPassword(@RequestBody @Valid ResetPasswordConfirmReq confirmReq) {
        return Result.success(accessService.confirmResetPassword(confirmReq));
    }

}
