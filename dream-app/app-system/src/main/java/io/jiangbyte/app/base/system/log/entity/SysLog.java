package io.jiangbyte.app.base.system.log.entity;

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

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 系统活动日志记录表
*/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "sys_log", autoResultMap = true)
@Schema(name = "SysLog", description = "系统活动日志记录表")
public class SysLog extends BaseEntity {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @SortConfig(type = SortType.NUMERIC_STRING)
    private String id;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "操作类型")
    private String operation;

    @Schema(description = "请求方法")
    private String method;

    @Schema(description = "请求参数")
    private String params;

    @Schema(description = "IP地址")
    private String ip;

    @Schema(description = "操作时间")
    private Date operationTime;

    @Schema(description = "日志分类")
    private String category;

    @Schema(description = "操作模块")
    private String module;

    @Schema(description = "操作描述")
    private String description;

    @Schema(description = "操作状态")
    private String status;

    @Schema(description = "日志消息")
    private String message;
}
