package io.jiangbyte.app.base.access.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserPublicAssociatedInfo {
    // UserInfo fields
    private Long accountId;
    private String nickname;
    private String avatar;
    private Integer gender;
    private Date birthday;
    private String signature;
    private String background;
    private String interests;
    private String website;
    private String github;
    private String gitee;
    private String blog;
    
    // UserProfile fields
    private String country;
    private String province;
    private String city;
    private Boolean showBirthday;
    private Boolean showLocation;
    
    // UserStats fields
    private Integer level;
    private Long exp;
    private Long totalExp;
    private Long postCount;
    private Long commentCount;
    private Long likeCount;
    private Long followCount;
    private Long fansCount;
}