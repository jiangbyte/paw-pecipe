package ${package.Controller};

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.framework.result.Result;
import ${package.Dto}.${entity}Dto;
import ${package.PageQuery}.${entity}PageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import ${package.Service}.${entity}Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
* @author ${author}
* @version v1.0
* @date ${date}
* @description ${table.comment!} 控制器
*/
@Tag(name = "${table.comment!}控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class ${entity}Controller {
    private final ${entity}Service ${table.entityPath}Service;

    @Operation(summary = "获取${table.comment?replace('表', '')}分页")
    @SaCheckPermission("/${table.name?replace('_', '/')}/page")
    @GetMapping("/${table.name?replace('_', '/')}/page")
    public Result<?> page(@ParameterObject ${entity}PageQuery req) {
        return Result.success(${table.entityPath}Service.page(req));
    }

    @Operation(summary = "添加${table.comment?replace('表', '')}")
    @SaCheckPermission("/${table.name?replace('_', '/')}/add")
    @PostMapping("/${table.name?replace('_', '/')}/add")
    public Result<?> add(@RequestBody @Valid ${entity}Dto req) {
        ${table.entityPath}Service.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑${table.comment?replace('表', '')}")
    @SaCheckPermission("/${table.name?replace('_', '/')}/edit")
    @PostMapping("/${table.name?replace('_', '/')}/edit")
    public Result<?> edit(@RequestBody @Valid ${entity}Dto req) {
        ${table.entityPath}Service.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除${table.comment?replace('表', '')}")
    @SaCheckPermission("/${table.name?replace('_', '/')}/delete")
    @PostMapping("/${table.name?replace('_', '/')}/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        ${table.entityPath}Service.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取${table.comment?replace('表', '')}详情")
    @SaCheckPermission("/${table.name?replace('_', '/')}/detail")
    @GetMapping("/${table.name?replace('_', '/')}/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(${table.entityPath}Service.detail(id));
    }

    @Operation(summary = "获取${table.comment?replace('表', '')}N最新")
    @SaCheckPermission("/${table.name?replace('_', '/')}/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(${table.entityPath}Service.latest(n));
    }

    @Operation(summary = "获取${table.comment?replace('表', '')}TopN")
    @SaCheckPermission("/${table.name?replace('_', '/')}/top")
    @GetMapping("/${table.name?replace('_', '/')}/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(${table.entityPath}Service.topN(n));
    }

}