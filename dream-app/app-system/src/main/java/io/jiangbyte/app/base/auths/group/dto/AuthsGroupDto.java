package io.jiangbyte.app.base.auths.group.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 用户组 编辑参数
*/
@Data
@Schema(name = "AuthsGroup", description = "用户组 编辑参数")
public class AuthsGroupDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "父级组ID")
    private String parentId;

    @Schema(description = "用户组名称")
    private String name;

    @Schema(description = "用户组编码")
    private String code;

    @Schema(description = "用户组描述")
    private String description;

    @Schema(description = "排序号，数字越小越靠前")
    private Short sort;

    @Schema(description = "管理员ID")
    private String adminId;

    @Schema(description = "最大用户数量限制")
    private Integer maxUserCount;

}