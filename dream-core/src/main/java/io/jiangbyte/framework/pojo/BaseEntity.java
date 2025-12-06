package io.jiangbyte.framework.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.dromara.core.trans.anno.Trans;
import org.dromara.core.trans.constant.TransType;
import org.dromara.core.trans.vo.TransPojo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Charlie Zhang
 * @version v1.0
 * @date 21/07/2025
 * @description 基础实体类
 */
@Data
@JsonIgnoreProperties(value = "transMap")
public class BaseEntity implements Serializable, TransPojo {
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 逻辑删除字段
     */
    @TableLogic
    private Boolean isDeleted;

    private Date deletedAt;

    private String deletedBy;

    /**
     * 记录创建的时间戳
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    /**
     * 创建者ID
     */
    @TableField(fill = FieldFill.INSERT)
    @Trans(type = TransType.SIMPLE, targetClassName = "io.jiangbyte.app.base.users.info.entity.UsersInfo", fields = {"avatar", "nickname"}, refs = {"createUserAvatar", "createUserName"})
    private String createdBy;

    /**
     * 头像
     */
    @TableField(exist = false)
    private String createUserAvatar;

    /**
     * 创建者名称
     */
    @TableField(exist = false)
    private String createUserNickname;

    /**
     * 记录最后更新的时间戳
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;

    /**
     * 记录最后更新的用户ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Trans(type = TransType.SIMPLE, targetClassName = "io.jiangbyte.app.base.users.info.entity.UsersInfo", fields = {"avatar", "nickname"}, refs = {"updateUserAvatar", "updateUserName"})
    private String updatedBy;

    /**
     * 头像
     */
    @TableField(exist = false)
    private String updateUserAvatar;

    /**
     * 更新者名称
     */
    @TableField(exist = false)
    private String updateUserNickname;
}
