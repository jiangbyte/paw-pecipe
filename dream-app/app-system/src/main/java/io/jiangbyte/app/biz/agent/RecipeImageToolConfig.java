package io.jiangbyte.app.biz.agent;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class RecipeImageToolConfig {

    private final WebClient webClient; // 注入 WebClient
    private final ObjectMapper objectMapper;

    @Bean
    @Description("根据菜谱名称获取封面图片，例如西红柿炒鸡蛋、麻婆豆腐等，如果是少油版西红柿泥蛋羹这样的菜谱，通过关键的词进行搜索，例如泥蛋羹")
    public Function<String, String> getImage() {
        return recipe -> {
            if (recipe == null || recipe.trim().isEmpty()) {
                log.warn("菜谱名称为空，无法获取图片");
                return "";
            }

            String encodedWords = URLEncoder.encode(recipe.trim(), StandardCharsets.UTF_8);
            String url = "https://cn.apihz.cn/api/img/apihzimgsougou.php?id=88888888&key=88888888&page=1&words=" + encodedWords;

            try {
                log.info("正在请求菜谱图片: {}", recipe);

                String response = webClient
                        .get()
                        .uri(url)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                if (response == null) {
                    log.warn("API 返回空响应");
                    return "";
                }

                JsonNode root = objectMapper.readTree(response);
                int code = root.path("code").asInt();

                if (code != 200) {
                    log.warn("API 返回错误码: {}, 响应: {}", code, response);
                    return "";
                }

                JsonNode resNode = root.path("res");
                List<String> urls;
                if (resNode.isArray() && !resNode.isEmpty()) {
                    urls = objectMapper.convertValue(resNode, objectMapper.getTypeFactory()
                            .constructCollectionType(List.class, String.class));
                } else {
                    urls = Collections.emptyList();
                }

                if (urls == null || urls.isEmpty()) { // 再加一层保险
                    log.warn("API 返回的 res 字段为空或无效");
                    return "";
                }

                String selectedUrl = urls.get(new Random().nextInt(urls.size()));
                log.info("成功获取菜谱图片: {} -> {}", recipe, selectedUrl);
                return selectedUrl;

            } catch (Exception e) {
                log.error("获取菜谱图片失败: {}", recipe, e);
                return "";
            }
        };
    }
}