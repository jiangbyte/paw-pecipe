package io.jiangbyte.app.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.jiangbyte.app.base.system.dict.entity.SysDict;
import io.jiangbyte.app.base.system.dict.mapper.SysDictMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.trans.service.impl.DictionaryTransService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author ZhangJiangHu
 * @version v1.0
 * @date 21/11/2025
 * @description TODO
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DictInit {
    private final SysDictMapper sysDictMapper;
    private final DictionaryTransService dictionaryTransService;

    @PostConstruct
    public void initDictCache() {
        CompletableFuture.runAsync(() -> {
            log.debug("开始异步初始化字典缓存...");

            try {
                // 一次性查询所有字典数据
                List<SysDict> allDicts = sysDictMapper.selectList(null);

                // 按字典类型分组
                Map<String, List<SysDict>> dictsByType = allDicts.stream()
                        .collect(Collectors.groupingBy(SysDict::getDictType));

                // 批量刷新缓存
                dictsByType.forEach((dictType, dictItems) -> {
                    Map<String, String> transMap = dictItems.stream()
                            .collect(Collectors.toMap(SysDict::getDictValue, SysDict::getDictLabel));
                    dictionaryTransService.refreshCache(dictType, transMap);
                });

                log.info("字典缓存异步初始化完成，共初始化 {} 个字典类型", dictsByType.size());
            } catch (Exception e) {
                log.error("字典缓存初始化失败", e);
            }
        });
    }

    public void refreshDictCache(String dictType) {
        List<SysDict> dictItems = sysDictMapper.selectList(new LambdaQueryWrapper<SysDict>()
                .eq(SysDict::getDictType, dictType));
        if (dictItems == null) {
            return;
        }

        Map<String, String> transMap = dictItems.stream()
                .collect(Collectors.toMap(SysDict::getDictValue, SysDict::getDictLabel));

        dictionaryTransService.refreshCache(dictType, transMap);
    }
}
