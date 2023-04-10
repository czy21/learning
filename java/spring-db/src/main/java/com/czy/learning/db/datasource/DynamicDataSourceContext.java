package com.czy.learning.db.datasource;


import com.czy.learning.db.annotation.DS;

public class DynamicDataSourceContext implements AutoCloseable {
    private static final ThreadLocal<String> key = new ThreadLocal<>();

    public static String get() {
        String key = DynamicDataSourceContext.key.get();
        try {
            return key == null ? (String) DS.class.getMethod("value").getDefaultValue() : key;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static void put(String key) {
        DynamicDataSourceContext.key.set(key);
    }

    @Override
    public void close() {
        key.remove();
    }
}
