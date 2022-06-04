package com.czy.learning.pulsar.core;


import com.czy.learning.pulsar.annotation.PulsarListener;
import org.apache.pulsar.client.api.*;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PulsarListenerScanner implements BeanPostProcessor {
    Map<String, Consumer<?>> consumerMap = new ConcurrentHashMap<>();
    PulsarClient pulsarClient;
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 2000, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    public PulsarListenerScanner(PulsarClient pulsarClient) {
        this.pulsarClient = pulsarClient;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        List<ListenerMethod> methods = new ArrayList<>();
        ReflectionUtils.doWithMethods(targetClass, method -> {
            PulsarListener listener = AnnotationUtils.findAnnotation(method, PulsarListener.class);
            if (listener != null) {
                methods.add(new ListenerMethod(method, listener));
            }
        }, ReflectionUtils.USER_DECLARED_METHODS);
        for (ListenerMethod lm : methods) {
            processListener(lm.annotation, lm.method, bean, beanName);
        }
        return bean;
    }

    public void processListener(PulsarListener pl, Method method, Object bean, String beanName) {
        Schema<?> schema = Schema.JSON(pl.clazz());
        try {
            ConsumerBuilder<?> consumerBuilder = pulsarClient.newConsumer(schema).topic(pl.topic())
                    .subscriptionName(StringUtils.hasLength(pl.subscriptionName())
                            ? pl.subscriptionName()
                            : String.join(".", beanName, method.getName()) + "-" + pl.clazz().getName()
                    )
                    .subscriptionType(pl.subscriptionType());
            Consumer<?> consumer;
            if (pl.batch()) {
                consumerBuilder.batchReceivePolicy(BatchReceivePolicy.builder()
                        .maxNumMessages(1000)
                        .maxNumBytes(10 * 1024 * 1024)
                        .timeout(100, TimeUnit.MILLISECONDS)
                        .build());
                consumer = consumerBuilder.subscribe();
                threadPoolExecutor.execute(() -> {
                    while (true) {
                        try {
                            Messages<?> messages = consumer.batchReceive();
                            List<Object> messageList = new ArrayList<>();
                            if (messages.size() > 0) {
                                messages.forEach(t -> {
                                    messageList.add(t.getValue());
                                });
                                method.invoke(bean, messageList);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                consumer = consumerBuilder.messageListener((consumer1, msg) -> {
                    try {
                        method.invoke(bean, msg.getValue());
                        consumer1.acknowledge(msg);
                    } catch (Exception e) {
                        consumer1.negativeAcknowledge(msg);
                        throw new RuntimeException(e);
                    }
                }).subscribe();
            }
            consumerMap.put(pl.topic(), consumer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static class ListenerMethod {

        final Method method;

        final PulsarListener annotation;

        ListenerMethod(Method method, PulsarListener annotation) {
            this.method = method;
            this.annotation = annotation;
        }

    }
}
