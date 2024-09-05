package com.learning.infranstructure.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleItemModel<T> implements TreeNode<T> {
    private String label;
    private T value;
    private String parentLabel;
    private T parentValue;
    private Map<String, Object> extra;
    private List<SimpleItemModel<T>> children;

    @Builder.Default
    private Integer sort = 0;

    public static <T> SimpleItemModel<T> of(String label, T value, String parentLabel, T parentValue) {
        return SimpleItemModel.<T>builder()
                .label(label)
                .value(value)
                .parentLabel(parentLabel)
                .parentValue(parentValue)
                .build();
    }

    public static <T> SimpleItemModel<T> of(String label, T value) {
        return of(label, value, null, null);
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

    public static <T> String translateByValue(List<SimpleItemModel<T>> list, T value) {
        return list.stream().filter(t -> t.getValue().equals(value)).map(SimpleItemModel::getLabel).findFirst().orElse(null);
    }

    public static <T> List<String> translateByValues(List<SimpleItemModel<T>> list, List<T> values) {
        return list.stream().filter(t -> values.contains(t.getValue())).map(SimpleItemModel::getLabel).collect(Collectors.toList());
    }
}
