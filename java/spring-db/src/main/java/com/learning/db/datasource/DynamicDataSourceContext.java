package com.learning.db.datasource;


import com.learning.db.annotation.DS;
import lombok.SneakyThrows;

public class DynamicDataSourceContext implements AutoCloseable {
    private static final ThreadLocal<String> key = new ThreadLocal<>();

    @SneakyThrows
    public static String get() {
        String key = DynamicDataSourceContext.key.get();
        return key == null ? (String) DS.class.getMethod("value").getDefaultValue() : key;
    }

    public static void put(String key) {
        DynamicDataSourceContext.key.set(key);
    }

    @Override
    public void close() {
        key.remove();
    }
}
