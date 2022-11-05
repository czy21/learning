package com.czy.learning.db.datasource;


import com.czy.learning.db.annotation.DS;
import com.czy.learning.db.aspect.RoutingDataSourceAspect;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class DynamicDataSourceConfigure {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceConfigure.class);
    DynamicDataSourceProperties dynamicDataSourceProperties;
    Map<Object, Object> dataSourceMap;

    public DynamicDataSourceConfigure(DynamicDataSourceProperties dynamicDataSourceProperties) {
        this.dynamicDataSourceProperties = dynamicDataSourceProperties;
        dataSourceMap = dynamicDataSourceProperties.getDatasource()
                .entrySet()
                .stream()
                .collect(HashMap::new,
                        (m, n) -> {
                            n.getValue().setPoolName(MessageFormat.format("datasource => {0}", n.getKey()));
                            HikariDataSource ds = new HikariDataSource(n.getValue());
                            m.put(n.getKey(), ds);
                        },
                        Map::putAll);
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource() throws NoSuchMethodException {
        RoutingDataSource rds = new RoutingDataSource();
        rds.setTargetDataSources(dataSourceMap);
        String master = (String) DS.class.getDeclaredMethod("value").getDefaultValue();
        rds.setDefaultTargetDataSource(dataSourceMap.get(master));
        return rds;
    }

    @Bean
    public RoutingDataSourceAspect routingDataSourceAspect() {
        return new RoutingDataSourceAspect();
    }

}
