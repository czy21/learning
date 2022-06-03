package com.learning.infranstructure.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.Map;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleItemModel<T> implements TreeNode<T> {
    private String label;
    private T value;
    private T parentValue;
    private String parentLabel;
    private Map<String, Object> extra;
    private List<SimpleItemModel<T>> children;

    private int sort;

    public static <T> SimpleItemModel<T> of(String label, T value, T parentValue) {
        return SimpleItemModel.<T>builder()
                .label(label)
                .value(value)
                .parentValue(parentValue)
                .build();
    }

    public static <T> SimpleItemModel<T> of(String label, T value) {
        return of(label, value, null);
    }

    @JsonIgnore
    @Override
    public T getId() {
        return value;
    }

    @Override
    public void setId(T id) {
        this.value = id;
    }

    @JsonIgnore
    @Override
    public T getParentId() {
        return parentValue;
    }

    @Override
    public void setParentId(T parentId) {
        this.parentValue = parentId;
    }

    @Override
    public void setChildren(List children) {
        this.children = children;
    }
}
