package io.jiangbyte.app.base.auths.account.entity;

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
* @description 核心账户表
*/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "auths_account", autoResultMap = true)
@Schema(name = "AuthsAccount", description = "核心账户表")
public class AuthsAccount extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @SortConfig(type = SortType.NUMERIC_STRING)
    private String id;

    @Schema(description = "用户名，登录标识")
    private String username;

    @Schema(description = "加密后的密码")
    private String password;

    @Schema(description = "邮箱地址")
    private String email;

    @Schema(description = "手机号码")
    private String telephone;

    @Schema(description = "账户状态：0-正常, 1-锁定, 2-禁用")
    @Trans(type = TransType.DICTIONARY, key = "SYS_ACCOUNT_STATUS")
    private Integer status;

    @Schema(description = "密码强度等级：0-3")
    private Integer passwordStrength;

    @Schema(description = "最后修改密码的时间")
    private Date lastPasswordChange;

    @Schema(description = "最后登录时间")
    private Date lastLoginTime;

    @Schema(description = "最后登录IP地址")
    private String lastLoginIp;

    @Schema(description = "登录次数统计")
    private Integer loginCount;
}
