package com.czy.learning.db.aspect;


import com.czy.learning.db.datasource.DynamicDataSourceContext;
import com.czy.learning.db.annotation.DS;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.text.MessageFormat;

@Aspect
@Slf4j
public class RoutingDataSourceAspect {

    @Before("@annotation(ds)")
    public void before(JoinPoint joinPoint, DS ds) {
        String key = ds.value();
        DynamicDataSourceContext.put(key);
        log.debug(MessageFormat.format("switch ds to {0}", key));
    }

    @After("@annotation(ds)")
    public void after(JoinPoint joinPoint, DS ds) throws Exception {
        DynamicDataSourceContext.put((String) DS.class.getMethod("value").getDefaultValue());
    }
}
