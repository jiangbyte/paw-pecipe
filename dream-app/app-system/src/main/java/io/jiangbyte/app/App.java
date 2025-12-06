package io.jiangbyte.app;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Charlie Zhang
 * @version v1.0
 * @date 27/09/2025
 * @description 系统应用
 */
@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableCaching  // 开启缓存
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(App.class, args);
        Environment env = run.getEnvironment();

        String port = env.getProperty("server.port");
        String contextPath = env.getProperty("server.servlet.context-path");

        String displayContextPath = (contextPath == null || contextPath.isEmpty()) ? "" : contextPath;

        log.info("Local: http://localhost:{}{}", port, displayContextPath);
        log.info("Docs: http://localhost:{}{}/doc.html", port, displayContextPath);
    }

    @RequiredArgsConstructor
    @RestController
    static class IndexController {
        private final Environment env;

        @GetMapping("/")
        public String index() {
            return "UP";
        }

    }
}
