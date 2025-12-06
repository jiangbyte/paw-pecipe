package io.jiangbyte.framework.pojo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author Charlie Zhang
 * @version v1.0
 * @date 22/07/2025
 * @description 通用分页
 */
public class BasePageRequest {
    private static final Integer PAGE_NUM_DEFAULT_VALUE = 1;

    private static final Integer PAGE_SIZE_DEFAULT_VALUE = 1;
    private static final Integer PAGE_SIZE_MAX_VALUE = 1000;

    public static <T> Page<T> Page(int page, int size) {
        // 如果小于0，则返回默认值
        if (page < 0) {
            page = PAGE_NUM_DEFAULT_VALUE;
        }
        // 如果size小于0，则返回默认值，否则返回size
        // 如果超出最大值最小值，使用边界值
        if (size < 0 || size > PAGE_SIZE_MAX_VALUE) {
            size = PAGE_SIZE_DEFAULT_VALUE;
        }
        // 如果小于默认值，则使用默认值
        size = size < PAGE_SIZE_DEFAULT_VALUE ? PAGE_SIZE_DEFAULT_VALUE : size;

        return Page.of(page, size);
    }
}
