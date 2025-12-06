package io.jiangbyte.framework.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Field;

@Slf4j
@Component
public class SortUtils {

    private static String databaseType;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        try {
            // 自动检测数据库类型
            databaseType = dataSource.getConnection().getMetaData().getDatabaseProductName().toLowerCase();
        } catch (Exception e) {
            databaseType = "mysql"; // 默认使用MySQL
        }
        log.info("Database type: {}", databaseType);
    }

    /**
     * 通用排序处理
     */
    public static <T> void handleSort(Class<T> entityClass,
                                      QueryWrapper<T> queryWrapper,
                                      String sortField,
                                      String sortOrder) {
        if (StrUtil.isBlank(sortField) || StrUtil.isBlank(sortOrder)) {
            applyDefaultSort(entityClass, queryWrapper);
            return;
        }

        boolean isAsc = "asc".equalsIgnoreCase(sortOrder);

        // 获取字段的排序配置
        SortConfig sortConfig = getFieldSortConfig(entityClass, sortField);

        // 应用排序
        if (sortConfig != null) {
            applyCustomSort(queryWrapper, sortField, isAsc, sortConfig);
        } else {
            // 普通字段排序
            queryWrapper.orderBy(true, isAsc, StrUtil.toUnderlineCase(sortField));
        }
    }

    /**
     * 获取字段的排序配置
     */
    private static <T> SortConfig getFieldSortConfig(Class<T> entityClass, String fieldName) {
        try {
            Field field = entityClass.getDeclaredField(fieldName);
            return field.getAnnotation(SortConfig.class);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    /**
     * 应用自定义排序（支持多数据库）
     */
    private static <T> void applyCustomSort(QueryWrapper<T> queryWrapper,
                                            String field,
                                            boolean isAsc,
                                            SortConfig config) {
        String orderSql;
        String underlineField = StrUtil.toUnderlineCase(field);

        switch (config.type()) {
            case NUMERIC_STRING:
                orderSql = buildNumericSortSql(underlineField, isAsc);
                break;
            case CHINESE_PINYIN:
                orderSql = buildChineseSortSql(underlineField, isAsc);
                break;
            case CUSTOM:
                orderSql = config.customSql() + (isAsc ? " ASC" : " DESC");
                break;
            default:
                orderSql = underlineField + (isAsc ? " ASC" : " DESC");
                break;
        }

        queryWrapper.last("ORDER BY " + orderSql);
    }

    /**
     * 构建数值排序SQL（支持多数据库）
     */
    private static String buildNumericSortSql(String field, boolean isAsc) {
        String order = isAsc ? "ASC" : "DESC";

        switch (getDbType()) {
            case "postgresql":
            case "postgres":
                // PostgreSQL: 使用::bigint或CAST
                return String.format("CAST(%s AS BIGINT) %s", field, order);
            case "mysql":
            case "mariadb":
                // MySQL: 使用CAST或CONVERT
                return String.format("CAST(%s AS UNSIGNED) %s", field, order);
            case "oracle":
                // Oracle: 使用TO_NUMBER
                return String.format("TO_NUMBER(%s) %s", field, order);
            case "sqlserver":
                // SQL Server: 使用CAST
                return String.format("CAST(%s AS BIGINT) %s", field, order);
            default:
                // 默认使用通用的CAST
                return String.format("CAST(%s AS BIGINT) %s", field, order);
        }
    }

    /**
     * 构建中文排序SQL（支持多数据库）
     */
    private static String buildChineseSortSql(String field, boolean isAsc) {
        String order = isAsc ? "ASC" : "DESC";

        switch (getDbType()) {
            case "postgresql":
            case "postgres":
                // PostgreSQL: 使用COLLATE
                return String.format("%s COLLATE \"zh_CN\" %s", field, order);
            case "mysql":
            case "mariadb":
                // MySQL: 使用CONVERT和gbk字符集
                return String.format("CONVERT(%s USING gbk) %s", field, order);
            case "oracle":
                // Oracle: 使用NLSSORT
                return String.format("NLSSORT(%s, 'NLS_SORT = SCHINESE_PINYIN_M') %s", field, order);
            case "sqlserver":
                // SQL Server: 使用COLLATE
                return String.format("%s COLLATE Chinese_PRC_CI_AS %s", field, order);
            default:
                // 默认直接排序
                return String.format("%s %s", field, order);
        }
    }

    /**
     * 应用默认排序（支持多数据库）
     */
    private static <T> void applyDefaultSort(Class<T> entityClass, QueryWrapper<T> queryWrapper) {
        boolean hasSortField = hasField(entityClass, "sort");
        boolean hasIdField = hasField(entityClass, "id");

        if (hasSortField && hasIdField) {
            // 同时有sort和id字段，合并排序
            String sortSql = "sort ASC, " + buildNumericSortSql("id", true);
            queryWrapper.last("ORDER BY " + sortSql);
        } else if (hasSortField) {
            // 只有sort字段
            queryWrapper.orderByAsc("sort");
        } else if (hasIdField) {
            // 只有id字段
            String idSortSql = buildNumericSortSql("id", true);
            queryWrapper.last("ORDER BY " + idSortSql);
        }
        // 如果都没有，就不排序
    }

    /**
     * 检查实体类是否有指定字段
     */
    private static <T> boolean hasField(Class<T> entityClass, String fieldName) {
        try {
            entityClass.getDeclaredField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    /**
     * 获取数据库类型
     */
    private static String getDbType() {
        return databaseType != null ? databaseType : "mysql";
    }
}