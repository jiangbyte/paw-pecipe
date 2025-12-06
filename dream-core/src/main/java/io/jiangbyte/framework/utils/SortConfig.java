package io.jiangbyte.framework.utils;

import io.jiangbyte.framework.enums.SortType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SortConfig {
    SortType type() default SortType.DEFAULT;
    String customSql() default "";
}
