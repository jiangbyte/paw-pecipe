package io.jiangbyte.app.exceptions;

import cn.dev33.satoken.exception.NotLoginException;
import io.jiangbyte.framework.exception.BusinessException;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.framework.result.ResultCode;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * @author charlie-zhang-code
 * @version v1.0
 * @date 2025/4/13
 * @description 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理业务异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public <T> Result<T> handleBizException(BusinessException e) {
        if (e.getResultCode() != null) {
            return Result.failure(e.getResultCode());
        }
        log.error("业务异常：{}", e.getMessage());
        e.printStackTrace();
        return Result.failure(e.getMessage());
    }

    /**
     * 处理请求地址不存在异常
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public <T> Result<T> processException(NoHandlerFoundException e) {
        log.error("请求地址不存在：{}", e.getMessage());
        e.printStackTrace();
        return Result.failure(ResultCode.NOT_FOUND, e.getMessage());
    }

    /**
     * 处理静态资源不存在异常
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public <T> Result<T> processNoResourceFoundException(NoResourceFoundException e) {
        log.error("请求地址不存在：{}", e.getMessage());
        return Result.failure(ResultCode.NOT_FOUND, e.getMessage());
    }

    /**
     * 处理运行时异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public <T> Result<T> processException(RuntimeException e) {
        log.error("系统异常：{}", e.getMessage());
        e.printStackTrace();
        return Result.failure(ResultCode.SYSTEM_ERROR, ResultCode.SYSTEM_ERROR.getMessage());
    }

    /**
     * 处理参数类型转换失败（如 @PathVariable 或 @RequestParam 类型不匹配）
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public <T> Result<T> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        String errorMsg = String.format("参数 '%s' 类型错误，应为 %s 类型",
                e.getName(),
                e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "未知");
        log.error("参数类型错误：{}", errorMsg);
        e.printStackTrace();
        return Result.failure(ResultCode.PARAM_ERROR, errorMsg);
    }

    /**
     * 处理 @RequestParam 参数校验失败异常（Spring 6+）
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public Result<?> handleHandlerMethodValidationException(ValidationException e) {
        String errorMsg = e.getMessage();
        log.error("参数校验异常：{}", errorMsg);
        e.printStackTrace();
        return Result.failure(ResultCode.PARAM_ERROR, errorMsg);
    }

    /**
     * 处理 @Validated 方法参数校验异常（兼容旧版）
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
                .orElse("参数校验失败");
        log.error("参数校验异常：{}", errorMsg);
        e.printStackTrace();
        return Result.failure(ResultCode.PARAM_ERROR, errorMsg);
    }

    /**
     * 未登录异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotLoginException.class)
    public <T> Result<T> handleNotLoginException(NotLoginException e) {
        log.error("未登录异常：{}", e.getMessage());
        e.printStackTrace();
        return Result.failure(ResultCode.NOT_LOGIN, e.getMessage());
    }

}
