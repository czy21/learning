package com.learning.db;

import com.learning.db.datasource.DynamicDataSourceConfigure;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DynamicDataSourceConfigure.class})
public class DBConfigure {
}
