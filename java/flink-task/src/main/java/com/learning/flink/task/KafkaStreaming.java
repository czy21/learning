package com.learning.flink.task;


import com.learning.domain.entity.constant.QueueConstant;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.formats.json.JsonDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.pulsar.shade.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;

public class KafkaStreaming {

    private ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        JsonDeserializationSchema<OrderDTO> orderDeserializer = new JsonDeserializationSchema<>(OrderDTO.class);
        KafkaSource<OrderDTO> source = KafkaSource.<OrderDTO>builder()
                .setBootstrapServers("192.168.2.18:9092,192.168.2.18:9093")
                .setTopics("input5Topic")
                .setGroupId(QueueConstant.GLOBAL_QUEUE_GROUP1)
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(orderDeserializer)
                .build();
        DataStream<OrderDTO> stream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");
        stream.map(t -> Tuple2.of(t.getProductId(), t.getTime() * 1000)).returns(Types.TUPLE(Types.LONG, Types.LONG))
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Tuple2<Long, Long>>forBoundedOutOfOrderness(Duration.ofSeconds(5))
                        .withTimestampAssigner((element, recordTimestamp) -> element.f1))
                .map(t -> Tuple2.of(t.f0, 1)).returns(Types.TUPLE(Types.LONG, Types.INT))
                .keyBy(t -> t.f0)
                .window(SlidingEventTimeWindows.of(Time.seconds(30), Time.seconds(10)))
                .sum(1)
                .addSink(JdbcSink.sink(
                        "insert into product_agg (product_id,count) values (?,?)",
                        (ps, t) -> {
                            ps.setLong(1, t.f0);
                            ps.setLong(2, t.f1);
                        },
                        new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                                .withUrl("jdbc:mysql://:3306/demo?user=&password=")
                                .withDriverName("com.mysql.cj.jdbc.Driver")
                                .build()));
        env.execute("kafka source job 1");
    }

}
