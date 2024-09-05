package com.learning.flink.task;


import com.learning.domain.entity.constant.QueueConstant;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.pulsar.source.PulsarSource;
import org.apache.flink.connector.pulsar.source.enumerator.cursor.StartCursor;
import org.apache.flink.connector.pulsar.source.reader.deserializer.PulsarDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.util.Collector;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionType;

import java.util.Map;
import java.util.UUID;

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
        DataStream<Map> stream = env.fromSource(pulsarSource, WatermarkStrategy.noWatermarks(), "PulsarSource1");
        stream.addSink(JdbcSink.sink(
                "insert into ent_book (id,name) values (?,?)",
                (ps, t) -> {
                    ps.setString(1, UUID.randomUUID().toString().replace("-", ""));
                    ps.setObject(2, t.get("name"));
                },
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                        .withUrl("jdbc:mysql://:3306/erp_local?user=&password=&serverTimezone=Asia/Shanghai&characterEncoding=utf8&useSSL=false&useUnicode=true&rewriteBatchStatements=true&useCursorFetch=true")
                        .withDriverName("com.mysql.cj.jdbc.Driver")
                        .build()));
        env.execute("PulsarJob1");
    }

}
