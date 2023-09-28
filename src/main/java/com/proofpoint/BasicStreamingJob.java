package com.proofpoint;

import com.amazonaws.services.kinesisanalytics.runtime.KinesisAnalyticsRuntime;
import com.proofpoint.models.ExchangeRate;
import com.proofpoint.models.Order;
import com.proofpoint.models.Result;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.kinesis.sink.KinesisStreamsSink;
import org.apache.flink.formats.json.JsonNodeDeserializationSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kinesis.FlinkKinesisConsumer;
import org.apache.flink.streaming.connectors.kinesis.config.AWSConfigConstants;
import org.apache.flink.streaming.connectors.kinesis.config.ConsumerConfigConstants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * A basic Flink Java application to run on Amazon Managed Service for Apache Flink,
 * with Kinesis Data Streams as source and sink.
 */
public class BasicStreamingJob {

    private static final String APPLICATION_CONFIG_GROUP = "FlinkApplicationProperties";
    private static final String DEFAULT_REGION = "us-east-1";
    private static final String DEFAULT_ORDER_STREAM = "OrderStream";
    private static final String DEFAULT_RATE_STREAM = "ExchangeRateStream";
    private static final String DEFAULT_OUTPUT_STREAM = "ResultStream";
    private static final String DEFAULT_AWS_ENDPOINT_URL = "http://localhost:4566";

    /**
     * Get configuration properties from Amazon Managed Service for Apache Flink runtime properties
     * GroupID "FlinkApplicationProperties", or from command line parameters when running locally
     */
    private static ParameterTool loadApplicationParameters(String[] args, StreamExecutionEnvironment env) throws IOException {
        if (env instanceof LocalStreamEnvironment) {
            return ParameterTool.fromArgs(args);
        } else {
            Map<String, Properties> applicationProperties = KinesisAnalyticsRuntime.getApplicationProperties();
            Properties flinkProperties = applicationProperties.get(APPLICATION_CONFIG_GROUP);
            if (flinkProperties == null) {
                throw new RuntimeException("Unable to load FlinkApplicationProperties properties from the Kinesis Analytics Runtime.");
            }
            Map<String, String> map = new HashMap<>(flinkProperties.size());
            flinkProperties.forEach((k, v) -> map.put((String) k, (String) v));
            return ParameterTool.fromMap(map);
        }
    }

    private static FlinkKinesisConsumer<ObjectNode> createOrderSource(ParameterTool applicationProperties) {
        return getFlinkKinesisConsumer(applicationProperties, DEFAULT_ORDER_STREAM);
    }

    private static FlinkKinesisConsumer<ObjectNode> createExchangeRateSource(ParameterTool applicationProperties) {
        return getFlinkKinesisConsumer(applicationProperties, DEFAULT_RATE_STREAM);
    }

    private static FlinkKinesisConsumer<ObjectNode> getFlinkKinesisConsumer(ParameterTool applicationProperties,
                                                                            String defaultRateStream) {
        Properties inputProperties = new Properties();
        inputProperties.setProperty(ConsumerConfigConstants.AWS_REGION,
                applicationProperties.get("InputStreamRegion", DEFAULT_REGION));
        inputProperties.setProperty(ConsumerConfigConstants.STREAM_INITIAL_POSITION, "TRIM_HORIZON");
        inputProperties.setProperty(ConsumerConfigConstants.SHARD_IDLE_INTERVAL_MILLIS, "30000");

        inputProperties.setProperty(AWSConfigConstants.AWS_ENDPOINT, DEFAULT_AWS_ENDPOINT_URL);


        return new FlinkKinesisConsumer<>(
                applicationProperties.get("InputStreamName", defaultRateStream),
                new JsonNodeDeserializationSchema(),
                inputProperties);
    }

    private static KinesisStreamsSink<Result> createSink(ParameterTool applicationProperties) {
        Properties outputProperties = new Properties();
        outputProperties.setProperty(AWSConfigConstants.AWS_REGION,
                applicationProperties.get("OutputStreamRegion", DEFAULT_REGION));
        outputProperties.setProperty(AWSConfigConstants.AWS_ENDPOINT, DEFAULT_AWS_ENDPOINT_URL);

        return KinesisStreamsSink.<Result>builder()
                .setKinesisClientProperties(outputProperties)
                .setSerializationSchema((SerializationSchema<Result>) element -> {
                    ObjectMapper mapper = new ObjectMapper();
                    byte[] output;
                    try {
                        output = mapper.writeValueAsString(element).getBytes();
                    } catch (Exception e) {
                        output = "".getBytes();
                    }
                    return output;
                })
                .setStreamName(outputProperties.getProperty("OutputStreamName", DEFAULT_OUTPUT_STREAM))
                .setPartitionKeyGenerator(element -> String.valueOf(element.hashCode()))
                .build();
    }


    public static void main(String[] args) throws Exception {
        // Set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        // Load application parameters
        final ParameterTool applicationParameters = loadApplicationParameters(args, env);

        SourceFunction<ObjectNode> orderSource = createOrderSource(applicationParameters);
        DataStream<ObjectNode> orderInput = env.addSource(orderSource, "Order Source");

        DataStream<Order> orderStream = orderInput
                .map((ObjectNode object) -> {
                    //For debugging input
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(object.toString(), Order.class);
                }).keyBy(order -> order.currency);


        SourceFunction<ObjectNode> exchangeSource = createExchangeRateSource(applicationParameters);
        DataStream<ObjectNode> exchangeInput = env.addSource(exchangeSource, "Exchange Source");

        DataStream<ExchangeRate> exchangeStream = exchangeInput
                .map((ObjectNode object) -> {
                    //For debugging input
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(object.toString(), ExchangeRate.class);
                }).keyBy(ExchangeRate -> ExchangeRate.currency);

        orderStream.join(exchangeStream)
                .where(order -> order.currency)
                .equalTo(exchangeRate -> exchangeRate.currency)
                .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
                .apply ((JoinFunction<Order, ExchangeRate, Result>) (order, exchangeRate) -> {
                    return new Result(order.id,
                            order.orderTime.toString(),
                            exchangeRate.rate,
                            order.amount*exchangeRate.rate);
                })
                .sinkTo(createSink(applicationParameters));


        env.execute("Flink streaming Java API skeleton");
    }
}
