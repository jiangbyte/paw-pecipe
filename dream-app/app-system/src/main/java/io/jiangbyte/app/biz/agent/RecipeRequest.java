// src/main/java/io/jiangbyte/app/biz/agent/model/RecipeRequest.java
package io.jiangbyte.app.biz.agent;

import lombok.Data;

@Data
public class RecipeRequest {
    private String vegetable;       // 必填：主食材
    private String oilLevel;        // 可选：少油、多油、正常
    private String speed;           // 可选：快手、慢炖
    private String difficulty;      // 可选：简单、中等、困难
    private String region;          // 可选：川菜、粤菜、东北等
}