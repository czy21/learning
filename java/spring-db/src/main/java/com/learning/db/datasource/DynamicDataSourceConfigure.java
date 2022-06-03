package com.learning.db.datasource;


import com.learning.db.annotation.DS;
import com.learning.db.aspect.RoutingDataSourceAspect;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DynamicDataSourceConfigure {
    DynamicDataSourceProperties dynamicDataSourceProperties;
    Map<Object, Object> dataSourceMap;

    public DynamicDataSourceConfigure(DynamicDataSourceProperties dynamicDataSourceProperties) {
        this.dynamicDataSourceProperties = dynamicDataSourceProperties;
        dataSourceMap = dynamicDataSourceProperties.getDatasource()
                .entrySet()
                .stream()
                .map(t -> {
                    t.getValue().setPoolName(String.join(" ", new String[]{"datasource", "=>", t.getKey()}));
                    var ds = new HikariDataSource(t.getValue());
                    return Map.of(t.getKey(), ds);
                })
                .collect(HashMap::new, Map::putAll, Map::putAll);
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource() throws NoSuchMethodException {
        RoutingDataSource rds = new RoutingDataSource();
        rds.setTargetDataSources(dataSourceMap);
        String master = (String) DS.class.getDeclaredMethod("value").getDefaultValue();
        if (!dataSourceMap.containsKey(master)) {
            log.warn("master datasource is null");
        }
        rds.setDefaultTargetDataSource(dataSourceMap.get(master));
        return rds;
    }

    @Bean
    public RoutingDataSourceAspect routingDataSourceAspect() {
        return new RoutingDataSourceAspect();
    }

}
