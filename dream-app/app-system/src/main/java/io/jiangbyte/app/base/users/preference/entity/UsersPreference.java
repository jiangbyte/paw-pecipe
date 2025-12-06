package io.jiangbyte.app.base.users.preference.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.jiangbyte.framework.pojo.BaseEntity;

import java.io.Serial;

import io.jiangbyte.framework.enums.SortType;
import io.jiangbyte.framework.utils.SortConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户偏好设置表
*/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "users_preference", autoResultMap = true)
@Schema(name = "UsersPreference", description = "用户偏好设置表")
public class UsersPreference extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @SortConfig(type = SortType.NUMERIC_STRING)
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
