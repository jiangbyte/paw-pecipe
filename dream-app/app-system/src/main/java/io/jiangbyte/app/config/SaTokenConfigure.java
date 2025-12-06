package io.jiangbyte.app.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpLogic;
import cn.hutool.json.JSONUtil;
import io.jiangbyte.app.config.properties.SecurityProperties;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.framework.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Sa-Token 权限认证 配置类
 */
@Slf4j
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }

    // 注册 Sa-Token 全局过滤器
    @Bean
    public SaServletFilter getSaServletFilter(SecurityProperties properties) {
        return new SaServletFilter()
                .addInclude("/**")
//                .setAuth(obj -> {
//                            SaRouter.match("/**")
//                                    .notMatch(properties.getIgnore().getUrls())
//                                    .check(r -> StpUtil.checkLogin());
//                            log.info("请求 path={}  提交 token={}", SaHolder.getRequest().getRequestPath(), StpUtil.getTokenValue());
//                        }
//                )
                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(obj -> {
                    SaHolder.getResponse()
                            // ---------- 设置跨域响应头 ----------
                            // 允许指定域访问跨域资源
                            .setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                            // 允许凭证
                            .setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true")
                            // 允许所有请求方式
                            .setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*")
                            // 允许的header参数
                            .setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*")
                            // 有效时间
                            .setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600")
                    ;

                    // 如果是预检请求，则立即返回到前端
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .back();
                })
                .setError(e -> JSONUtil.toJsonStr(Result.failure(ResultCode.UNAUTHORIZED, ResultCode.UNAUTHORIZED.getMessage())));
    }
}
