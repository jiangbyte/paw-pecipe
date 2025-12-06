package io.jiangbyte.app.base.auths.role.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 角色 编辑参数
*/
@Data
@Schema(name = "AuthsRole", description = "角色 编辑参数")
public class AuthsRoleDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "数据权限范围")
    private String dataScope;

    @Schema(description = "角色描述")
    private String description;

    @Schema(description = "分配的用户组ID列表(JSON数组)")
    private String assignGroupIds;

}