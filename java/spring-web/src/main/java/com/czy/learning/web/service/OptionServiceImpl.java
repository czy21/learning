package com.czy.learning.web.service;

import com.czy.learning.web.annotation.Option;
import com.czy.learning.infranstructure.model.SimpleItemModel;
import org.graalvm.collections.Pair;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class OptionServiceImpl implements BeanPostProcessor, OptionService {

    Map<String, Pair<Object, Method>> optionMap = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, List<? extends SimpleItemModel<?>>> findByKeys(Set<String> keys) {
        return keys.stream().collect(HashMap::new, (m, t) -> {
            List<? extends SimpleItemModel<?>> v = new ArrayList<>();
            try {
                Pair<Object, Method> optionPair = optionMap.get(t);
                if (optionPair != null) {
                    v = (List<? extends SimpleItemModel<?>>) optionPair.getRight().invoke(optionPair.getLeft());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            m.put(t, Optional.ofNullable(v).orElse(new ArrayList<>()));
        }, Map::putAll);
    }

    @Override
    public <T> List<SimpleItemModel<T>> findByKey(String key) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        ReflectionUtils.doWithMethods(targetClass, method -> {
            Option option = AnnotationUtils.findAnnotation(method, Option.class);
            if (option != null) {
                String k = option.value();
                Pair<Object, Method> v = Pair.create(bean, method);
                Pair<Object, Method> u = optionMap.putIfAbsent(k, v);
                if (u != null) {
                    throw new IllegalStateException(String.format(
                            "Duplicate key %s (attempted merging values %s and %s)",
                            k, u, v));
                }
            }
        }, ReflectionUtils.USER_DECLARED_METHODS);
        return bean;
    }
}
