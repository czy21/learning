

package com.czy.learning.flink.task;


import com.czy.learning.domain.entity.constant.QueueConstant;
import com.czy.learning.flink.common.serialization.MapDeserializer;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Map;

public class KafkaStreaming {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        KafkaSource<Map<String, Object>> source = KafkaSource.<Map<String, Object>>builder()
                .setBootstrapServers("192.168.2.18:9092,192.168.2.18:9093")
                .setTopics("input5Topic")
                .setGroupId(QueueConstant.GLOBAL_QUEUE_GROUP1)
                .setStartingOffsets(OffsetsInitializer.latest())
                .setDeserializer(KafkaRecordDeserializationSchema.valueOnly(MapDeserializer.class))
                .build();
        DataStream<Map<String, Object>> stream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");
        stream.print();
        env.execute("kafka source job 1");
    }

}
