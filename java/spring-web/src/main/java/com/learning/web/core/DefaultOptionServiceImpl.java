package com.learning.web.core;

import com.learning.web.annotation.Option;
import com.learning.web.annotation.OptionProvider;
import com.learning.web.model.SimpleItemModel;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class DefaultOptionServiceImpl implements ApplicationContextAware, OptionService {

    Map<String, Supplier<List<? extends SimpleItemModel<?>>>> optionMap = new ConcurrentHashMap<>();

    @Override
    public Map<String, List<? extends SimpleItemModel<?>>> findByKeys(Set<String> keys) {
        return keys.stream().collect(HashMap::new, (m, t) -> {
            List<? extends SimpleItemModel<?>> v = new ArrayList<>();
            try {
                Supplier<List<? extends SimpleItemModel<?>>> vSupplier = optionMap.get(t);
                if (vSupplier != null) {
                    v = Optional.of(vSupplier).map(Supplier::get).orElse(new ArrayList<>());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            m.put(t, v);
        }, Map::putAll);
    }

    @Override
    public <T> List<SimpleItemModel<T>> findByKey(String key) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> optionProviderBeans = applicationContext.getBeansWithAnnotation(OptionProvider.class);
        for (Object bean : optionProviderBeans.values()) {
            Class<?> targetClass = AopUtils.getTargetClass(bean);
            ReflectionUtils.doWithMethods(targetClass, method -> {
                Option option = AnnotationUtils.findAnnotation(method, Option.class);
                if (option != null) {
                    String k = option.value();
                    Supplier<List<? extends SimpleItemModel<?>>> v = () -> {
                        try {
                            return (List<? extends SimpleItemModel<?>>) method.invoke(bean);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    };
                    Supplier<List<? extends SimpleItemModel<?>>> u = optionMap.putIfAbsent(k, v);
                    if (u != null) {
                        throw new IllegalStateException(String.format(
                                "Duplicate key %s method %s",
                                k, bean.getClass().getName() + "." + method.getName()));
                    }
                }
            }, ReflectionUtils.USER_DECLARED_METHODS);
        }
    }
}
