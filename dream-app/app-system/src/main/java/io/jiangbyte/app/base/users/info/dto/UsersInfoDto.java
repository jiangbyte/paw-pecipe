package io.jiangbyte.app.base.users.info.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.util.Date;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户基本信息 编辑参数
*/
@Data
@Schema(name = "UsersInfo", description = "用户基本信息 编辑参数")
public class UsersInfoDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "账户ID")
    private String accountId;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "性别：0-未知 1-男 2-女")
    private Short gender;

    @Schema(description = "生日")
    private Date birthday;

    @Schema(description = "个性签名")
    private String signature;

    @Schema(description = "个人背景图片")
    private String background;

    @Schema(description = "兴趣标签")
    private String interests;

    @Schema(description = "个人网站")
    private String website;

    @Schema(description = "GitHub")
    private String github;

    @Schema(description = "GitTee")
    private String gitee;

    @Schema(description = "博客")
    private String blog;

}