package io.jiangbyte.app.base.users.profile.entity;

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
* @description 用户档案详情表
*/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "users_profile", autoResultMap = true)
@Schema(name = "UsersProfile", description = "用户档案详情表")
public class UsersProfile extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @SortConfig(type = SortType.NUMERIC_STRING)
    private String id;

    @Schema(description = "账户ID")
    private String accountId;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "学校")
    private String school;

    @Schema(description = "专业")
    private String major;

    @Schema(description = "学号")
    private String studentId;

    @Schema(description = "公司")
    private String company;

    @Schema(description = "职位")
    private String jobTitle;

    @Schema(description = "行业")
    private String industry;

    @Schema(description = "国家")
    private String country;

    @Schema(description = "省份")
    private String province;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "详细地址")
    private String location;

    @Schema(description = "QQ")
    private String qq;

    @Schema(description = "微信")
    private String wechat;

    @Schema(description = "是否显示生日")
    private Boolean showBirthday;

    @Schema(description = "是否显示地理位置")
    private Boolean showLocation;
}
