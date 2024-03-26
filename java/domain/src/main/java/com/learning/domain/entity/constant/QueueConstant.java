package com.learning.domain.entity.constant;

public class QueueConstant {
    public static String GLOBAL_QUEUE_PREFIX = "learning";
    public static String GLOBAL_QUEUE_TOPIC1 = String.join("-", GLOBAL_QUEUE_PREFIX, "topic1");
    public static String GLOBAL_QUEUE_GROUP1 = String.join("-", GLOBAL_QUEUE_PREFIX, "group1");
}
