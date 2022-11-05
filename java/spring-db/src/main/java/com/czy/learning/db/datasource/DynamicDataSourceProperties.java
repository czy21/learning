package com.czy.learning.db.datasource;

import com.zaxxer.hikari.HikariConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties(prefix = "spring.datasource.dynamic")
public class DynamicDataSourceProperties {
    private Map<String, HikariConfig> datasource;

    public Map<String, HikariConfig> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, HikariConfig> datasource) {
        this.datasource = datasource;
    }
}
