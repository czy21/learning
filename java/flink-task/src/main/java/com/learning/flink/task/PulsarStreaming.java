

package com.learning.flink.task;


import com.learning.domain.entity.constant.QueueConstant;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.pulsar.source.PulsarSource;
import org.apache.flink.connector.pulsar.source.enumerator.cursor.StartCursor;
import org.apache.flink.connector.pulsar.source.reader.deserializer.PulsarDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.pulsar.client.api.SubscriptionType;

public class PulsarStreaming {

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        PulsarSource<String> pulsarSource = PulsarSource.builder()
                .setServiceUrl("pulsar://192.168.2.25:6650")
                .setStartCursor(StartCursor.earliest())
                .setTopics(QueueConstant.GLOBAL_QUEUE_TOPIC1)
                .setDeserializationSchema(PulsarDeserializationSchema.flinkSchema(new SimpleStringSchema()))
                .setSubscriptionName(QueueConstant.GLOBAL_QUEUE_GROUP1)
                .setSubscriptionType(SubscriptionType.Exclusive)
                .build();
        DataStream<String> stream = env.fromSource(pulsarSource, WatermarkStrategy.noWatermarks(), "Pulsar Source");
        stream.print();
        env.execute("pulsar source job 1");
    }

}
