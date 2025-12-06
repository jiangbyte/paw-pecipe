package ${package.Entity};

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
<#list table.importPackages as pkg>
import ${pkg};
</#list>
import java.io.Serial;
import java.util.Date;
import io.jiangbyte.framework.enums.SortType;
import io.jiangbyte.framework.utils.SortConfig;
<#if springdoc>
import io.swagger.v3.oas.annotations.media.Schema;
</#if>
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
</#if>

/**
* @author ${author}
* @version v1.0
* @date ${date}
* @description ${table.comment!}
*/
<#if entityLombokModel>
    <#if superEntityClass??>
@EqualsAndHashCode(callSuper = true)
    </#if>
@Data
</#if>
<#if table.convert>
@TableName(value = "${schemaName}${table.name}", autoResultMap = true)
</#if>
<#if springdoc>
@Schema(name = "${entity}", description = "${table.comment!}")
</#if>
<#if superEntityClass??>
public class ${entity} extends ${superEntityClass}<#if activeRecord><${entity}></#if> {
</#if>
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if !["isDeleted", "deletedAt", "deletedBy", "createdAt", "createdBy", "createdBy", "updatedAt", "updatedBy"]?seq_contains(field.propertyName)>
        <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    @Schema(description = "${field.comment}")
<#--    <#if field.keyFlag>-->
<#--        <#if field.keyIdentityFlag>-->
<#--    @TableId(value = "${field.annotationColumnName}", type = IdType.${idType})-->
<#--        </#if>-->
<#--    </#if>-->
        <#if ["id"]?seq_contains(field.propertyName)>
    @TableId(value = "id", type = IdType.ASSIGN_ID)
        </#if>
        <#if ["id", "sort"]?seq_contains(field.propertyName)>
    @SortConfig(type = SortType.NUMERIC_STRING)
        </#if>
    <#-- 类型转换处理 -->
        <#if field.propertyType?contains("Date") || field.propertyType?contains("Timestamp") || field.propertyType?contains("LocalDateTime")>
        <#-- 时间类型统一使用 Date -->
    private Date ${field.propertyName};
<#--        <#elseif field.propertyType?contains("tinyint") || field.propertyType?contains("TINYINT")>-->
<#--    private Integer ${field.propertyName};-->
        <#else>
    private ${field.propertyType} ${field.propertyName};
        </#if>
    </#if>
</#list>
<#------------  END 字段循环遍历  ---------->
}
