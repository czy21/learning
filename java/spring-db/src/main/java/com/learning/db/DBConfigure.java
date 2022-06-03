package com.learning.db;

import com.learning.db.aspect.RoutingDataSourceAspect;
import com.learning.db.datasource.DynamicDataSourceConfigure;
import com.learning.db.datasource.DynamicDataSourceProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@AutoConfigureBefore(value = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(value = {DynamicDataSourceProperties.class})
@Import({DynamicDataSourceConfigure.class})
public class DBConfigure {
}
