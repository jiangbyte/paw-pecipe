package io.jiangbyte.app.base.system.log.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.util.Date;

/**
* @author Charlie Zhang
* @version v1.0
* @date 2025-11-25
* @description 系统活动日志记录 编辑参数
*/
@Data
@Schema(name = "SysLog", description = "系统活动日志记录 编辑参数")
public class SysLogDto implements Serializable {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
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