package com.learning.db;

import com.learning.db.datasource.DynamicDataSourceConfigure;
import com.learning.db.datasource.DynamicDataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(value = {DynamicDataSourceProperties.class})
@Import({DynamicDataSourceConfigure.class})
public class DBConfigure {
}
