package io.jiangbyte.app.base.auths.role.entity;

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
* @description 角色表
*/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "auths_role", autoResultMap = true)
@Schema(name = "AuthsRole", description = "角色表")
public class AuthsRole extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @SortConfig(type = SortType.NUMERIC_STRING)
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
