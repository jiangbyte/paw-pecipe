package io.jiangbyte.framework.option;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author charlie-zhang-code
 * @version v1.0
 * @date 2025/4/13
 * @description 下拉选项对象
 */
@Data
@NoArgsConstructor
public class LabelOptionWithCount<T> implements Serializable {

    private T value;

    private String text;

    private Integer count;

    private List<LabelOptionWithCount<T>> children;

    public LabelOptionWithCount(T value, String text) {
        this.value = value;
        this.text = text;
    }

    public LabelOptionWithCount(T value, String text, Integer count) {
        this.value = value;
        this.text = text;
        this.count = count;
    }

    public LabelOptionWithCount(T value, String text, List<LabelOptionWithCount<T>> children) {
        this.value = value;
        this.text = text;
        this.children = children;
    }

    public LabelOptionWithCount(T value, String text, Integer count, List<LabelOptionWithCount<T>> children) {
        this.value = value;
        this.text = text;
        this.count = count;
        this.children = children;
    }
}
