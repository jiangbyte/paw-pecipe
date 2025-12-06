package io.jiangbyte.app.base.auths.account.entity;

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
* @description 账户用户组关联表
*/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "auths_account_group", autoResultMap = true)
@Schema(name = "AuthsAccountGroup", description = "账户用户组关联表")
public class AuthsAccountGroup extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @SortConfig(type = SortType.NUMERIC_STRING)
    private String id;

    @Schema(description = "账户ID")
    private String accountId;

    @Schema(description = "角色ID")
    private String groupId;
}
