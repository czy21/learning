package com.czy.learning.db.aspect;


import com.czy.learning.db.datasource.DynamicDataSourceContext;
import com.czy.learning.db.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

@Aspect
public class RoutingDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(RoutingDataSourceAspect.class);

    @Before("@annotation(ds)")
    public void before(JoinPoint joinPoint, DS ds) {
        String key = ds.value();
        DynamicDataSourceContext.put(key);
        logger.debug(MessageFormat.format("switch ds to {0}", key));
    }

    @After("@annotation(ds)")
    public void after(JoinPoint joinPoint, DS ds) throws Exception {
        DynamicDataSourceContext.put((String) DS.class.getMethod("value").getDefaultValue());
    }
}
