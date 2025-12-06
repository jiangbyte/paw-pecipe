package io.jiangbyte.app.base.config.item.dto;

import lombok.Data;

import java.util.List;

/**
 * @author ZhangJiangHu
 * @version v1.0
 * @date 21/11/2025
 * @description TODO
 */
@Data
public class WebsiteConfigInfo {
    // 网站信息
    private String websiteName;
    private String websiteLogo;
    private String websiteDescription;
    private String websiteKeywords;
    private String websiteAuthor;
    private String websiteCopyright;
    private String websiteVersion;

    // 社交信息
    private String contactQQ;
    private String contactEmail;
    private String contactWeChat;

    private List<SocialLink> socialLinks;

    @Data
    public static class SocialLink {
        private String image;
        private String title;
        private String url;
    }
}
