package io.jiangbyte.app.biz.flashsalerecord.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.jiangbyte.app.biz.flashsalerecord.dto.FlashSaleRecordReq;
import io.jiangbyte.framework.result.Result;
import io.jiangbyte.app.biz.flashsalerecord.dto.BizFlashSaleRecordDto;
import io.jiangbyte.app.biz.flashsalerecord.dto.BizFlashSaleRecordPageQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.jiangbyte.app.biz.flashsalerecord.service.BizFlashSaleRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Charlie Zhang
 * @version v1.0
 * @date 2025-12-03
 * @description 秒杀资格记录表 控制器
 */
@Tag(name = "秒杀资格记录表控制器")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@Validated
public class BizFlashSaleRecordController {
    private final BizFlashSaleRecordService bizFlashSaleRecordService;

    @Operation(summary = "获取秒杀资格记录分页")
    @SaCheckPermission("/biz/flash/sale/record/page")
    @GetMapping("/biz/flash/sale/record/page")
    public Result<?> page(@ParameterObject BizFlashSaleRecordPageQuery req) {
        return Result.success(bizFlashSaleRecordService.page(req));
    }

    @Operation(summary = "添加秒杀资格记录")
    @SaCheckPermission("/biz/flash/sale/record/add")
    @PostMapping("/biz/flash/sale/record/add")
    public Result<?> add(@RequestBody @Valid BizFlashSaleRecordDto req) {
        bizFlashSaleRecordService.add(req);
        return Result.success();
    }

    @Operation(summary = "编辑秒杀资格记录")
    @SaCheckPermission("/biz/flash/sale/record/edit")
    @PostMapping("/biz/flash/sale/record/edit")
    public Result<?> edit(@RequestBody @Valid BizFlashSaleRecordDto req) {
        bizFlashSaleRecordService.edit(req);
        return Result.success();
    }

    @Operation(summary = "删除秒杀资格记录")
    @SaCheckPermission("/biz/flash/sale/record/delete")
    @PostMapping("/biz/flash/sale/record/delete")
    public Result<?> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空") List<String> ids) {
        bizFlashSaleRecordService.delete(ids);
        return Result.success();
    }

    @Operation(summary = "获取秒杀资格记录详情")
    @SaCheckPermission("/biz/flash/sale/record/detail")
    @GetMapping("/biz/flash/sale/record/detail/{id}")
    public Result<?> detail(@PathVariable("id") String id) {
        return Result.success(bizFlashSaleRecordService.detail(id));
    }

    @Operation(summary = "获取秒杀资格记录N最新")
    @SaCheckPermission("/biz/flash/sale/record/latest")
    public Result<?> latest(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizFlashSaleRecordService.latest(n));
    }

    @Operation(summary = "获取秒杀资格记录TopN")
    @SaCheckPermission("/biz/flash/sale/record/top")
    @GetMapping("/biz/flash/sale/record/top")
    public Result<?> topN(@RequestParam(value = "n", required = false) Integer n) {
        return Result.success(bizFlashSaleRecordService.topN(n));
    }


    // tryAcquireFlashSaleRecord
    @Operation(summary = "尝试获取秒杀资格")
    @PostMapping("/biz/flash/sale/record/try")
    public Result<?> tryAcquireFlashSaleRecord(@RequestBody @Valid FlashSaleRecordReq req) {
        return Result.success(bizFlashSaleRecordService.tryAcquireFlashSaleRecord(req));
    }
}