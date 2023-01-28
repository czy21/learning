package com.czy.learning.web.service;

import com.czy.learning.infranstructure.model.SimpleItemModel;
import com.czy.learning.web.annotation.Option;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OptionServiceImpl implements BeanPostProcessor, OptionService {

    Map<String, MutablePair<Object, Method>> optionMap = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, List<? extends SimpleItemModel<?>>> findByKeys(Set<String> keys) {
        return keys.stream().collect(HashMap::new, (m, n) -> {
            List<? extends SimpleItemModel<?>> v = new ArrayList<>();
            try {
                MutablePair<Object, Method> optionPair = optionMap.get(n);
                if (optionPair != null) {
                    v = (List<? extends SimpleItemModel<?>>) optionPair.getRight().invoke(optionPair.getLeft());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            m.put(n, Optional.ofNullable(v).orElse(new ArrayList<>()));
        }, Map::putAll);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<SimpleItemModel<T>> findByKey(String key) {
        Set<String> keys = new HashSet<>();
        keys.add(key);
        return (List)this.findByKeys(keys).entrySet().stream().findFirst().map(Map.Entry::getValue).orElse(new ArrayList<>());
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        ReflectionUtils.doWithMethods(targetClass, method -> {
            Option option = AnnotationUtils.findAnnotation(method, Option.class);
            if (option != null) {
                String k = option.value();
                MutablePair<Object, Method> v = MutablePair.of(bean, method);
                MutablePair<Object, Method> u = optionMap.putIfAbsent(k, v);
                if (u != null) {
                    throw new IllegalStateException(String.format("Duplicate key %s (attempted merging values %s and %s)", k, u, v));
                }
            }
        }, ReflectionUtils.USER_DECLARED_METHODS);
        return bean;
    }
}
