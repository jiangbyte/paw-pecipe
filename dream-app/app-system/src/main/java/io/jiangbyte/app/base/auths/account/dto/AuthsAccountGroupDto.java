package io.jiangbyte.app.base.auths.account.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 账户用户组关联 编辑参数
*/
@Data
@Schema(name = "AuthsAccountGroup", description = "账户用户组关联 编辑参数")
public class AuthsAccountGroupDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "账户ID")
    private String accountId;

    @Schema(description = "角色ID")
    private String groupId;

}