package com.czy.learning.flink.task;


import com.czy.learning.domain.entity.constant.QueueConstant;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.formats.json.JsonDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.time.Duration;

public class KafkaStreaming {

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
        DataStream<Tuple2<Long, Long>> orderNoTimeTuple = stream
                .map(t -> Tuple2.of(t.getOrderNo(), t.getTime()))
                .returns(Types.TUPLE(Types.LONG, Types.LONG));
        DataStream<Tuple2<Long, Long>> orderNoTimeWaterLine = orderNoTimeTuple
                .assignTimestampsAndWatermarks(
                        WatermarkStrategy
                                .<Tuple2<Long, Long>>forBoundedOutOfOrderness(Duration.ofSeconds(1))
                                .withTimestampAssigner((SerializableTimestampAssigner<Tuple2<Long, Long>>) (element, recordTimestamp) -> element.f1)
                )
                .returns(Types.TUPLE(Types.LONG, Types.LONG));
        DataStream<Tuple2<Long, Integer>> kvDS = orderNoTimeWaterLine.map(t -> Tuple2.of(t.f0, 1)).returns(Types.TUPLE(Types.LONG, Types.INT));
        KeyedStream<Tuple2<Long, Integer>, Long> keyByDS = kvDS.keyBy(t -> t.f0);
        WindowedStream<Tuple2<Long, Integer>, Long, TimeWindow> keyByDS1 = keyByDS.window(SlidingEventTimeWindows.of(Time.minutes(1), Time.seconds(5)));
        keyByDS1.sum(1).print();
        env.execute("kafka source job 1");
    }

}
