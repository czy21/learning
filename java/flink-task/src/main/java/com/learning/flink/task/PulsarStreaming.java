

package com.learning.flink.task;


import com.learning.domain.entity.constant.QueueConstant;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.pulsar.source.PulsarSource;
import org.apache.flink.connector.pulsar.source.enumerator.cursor.StartCursor;
import org.apache.flink.connector.pulsar.source.reader.deserializer.PulsarDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionType;

import java.util.Map;

public class PulsarStreaming {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        PulsarSource<Map> pulsarSource = PulsarSource.builder()
                .setServiceUrl("pulsar://pulsar-proxy.cluster.com:6650")
                .setAdminUrl("http://pulsar-proxy.cluster.com")
                .setStartCursor(StartCursor.latest())
                .setTopics(QueueConstant.GLOBAL_QUEUE_TOPIC1)
                .setDeserializationSchema(PulsarDeserializationSchema.pulsarSchema(Schema.JSON(Map.class), Map.class))
                .setSubscriptionName(QueueConstant.GLOBAL_QUEUE_GROUP1)
                .setSubscriptionType(SubscriptionType.Exclusive)
                .build();
        DataStream<Map> stream = env.fromSource(pulsarSource, WatermarkStrategy.noWatermarks(), "Pulsar Source");
        stream.print();
        env.execute("pulsar source job 1");
    }

}
