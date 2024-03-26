package com.learning.mybatis.one;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
    public class PersonBeanPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            if (bean.getClass() == Person.class) {
                System.out.println("调用postProcessBeforeInitialization...");
            }
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            if (bean.getClass() == Person.class) {
                System.out.println("调用postProcessAfterInitialization...");
            }
            return bean;
        }
    }