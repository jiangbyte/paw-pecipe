package io.jiangbyte.app.base.users.preference.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户偏好设置 编辑参数
*/
@Data
@Schema(name = "UsersPreference", description = "用户偏好设置 编辑参数")
public class UsersPreferenceDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "账户ID")
    private String accountId;

    @Schema(description = "主题")
    private String theme;

    @Schema(description = "系统语言")
    private String language;

    @Schema(description = "邮件通知")
    private Boolean emailNotifications;

    @Schema(description = "推送通知")
    private Boolean pushNotifications;

    @Schema(description = "允许私信")
    private Boolean allowDirectMessage;

}