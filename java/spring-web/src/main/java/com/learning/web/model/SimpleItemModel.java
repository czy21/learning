package com.learning.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode
public class SimpleItemModel<T> {
    private String label;
    private T value;
    private T parentValue;
    private Object extra;
    private List<SimpleItemModel<T>> children;

    private SimpleItemModel(String label, T value) {
        this.label = label;
        this.value = value;
    }

    public static <T> SimpleItemModel<T> of(String label, T value) {
        return new SimpleItemModel<>(label, value);
    }

}
