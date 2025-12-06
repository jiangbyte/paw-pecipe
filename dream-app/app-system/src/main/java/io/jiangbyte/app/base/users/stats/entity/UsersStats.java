package io.jiangbyte.app.base.users.stats.entity;

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
* @description 用户统计信息表
*/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "users_stats", autoResultMap = true)
@Schema(name = "UsersStats", description = "用户统计信息表")
public class UsersStats extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @SortConfig(type = SortType.NUMERIC_STRING)
    private String id;

    @Schema(description = "账户ID")
    private String accountId;

    @Schema(description = "等级")
    private Integer level;

    @Schema(description = "经验值")
    private Long exp;

    @Schema(description = "累计经验值")
    private Long totalExp;

    @Schema(description = "登录天数")
    private Integer loginDays;

    @Schema(description = "连续登录天数")
    private Integer continuousLoginDays;

    @Schema(description = "发帖数")
    private Long postCount;

    @Schema(description = "评论数")
    private Long commentCount;

    @Schema(description = "获赞数")
    private Long likeCount;

    @Schema(description = "关注数")
    private Long followCount;

    @Schema(description = "粉丝数")
    private Long fansCount;
}
