package io.jiangbyte.app.base.users.info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.jiangbyte.framework.pojo.BaseEntity;

import java.io.Serial;
import java.util.Date;
import io.jiangbyte.framework.enums.SortType;
import io.jiangbyte.framework.utils.SortConfig;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户基本信息表
*/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "users_info", autoResultMap = true)
@Schema(name = "UsersInfo", description = "用户基本信息表")
public class UsersInfo extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @SortConfig(type = SortType.NUMERIC_STRING)
    private String id;

    @Schema(description = "账户ID")
    private String accountId;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "性别：0-未知 1-男 2-女")
    @Trans(type = TransType.DICTIONARY, key = "SYS_GENDER")
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
