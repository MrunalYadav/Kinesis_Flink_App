package com.proofpoint;

import com.amazonaws.services.kinesisanalytics.runtime.KinesisAnalyticsRuntime;
import com.proofpoint.models.CompanyTag;
import com.proofpoint.models.CompanyUserTagResult;
import com.proofpoint.models.UserTag;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.kinesis.sink.KinesisStreamsSink;
import org.apache.flink.formats.json.JsonNodeDeserializationSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kinesis.FlinkKinesisConsumer;
import org.apache.flink.streaming.connectors.kinesis.config.AWSConfigConstants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * A basic Flink Java application to run on Amazon Managed Service for Apache Flink,
 * with Kinesis Data Streams as source and sink.
 */
public class CompanyUserTagStreamingJob {

    private static final String APPLICATION_CONFIG_GROUP = "FlinkApplicationProperties";
    private static final String DEFAULT_CT_STREAM = "CompanyTagStream";
    private static final String DEFAULT_UT_STREAM = "UserTagStream";
    private static final String DEFAULT_OUTPUT_STREAM = "CompanyUserTagResultStream";
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

    private static FlinkKinesisConsumer<ObjectNode> createCTSource(Properties applicationProperties) {

        String companyTagStream = applicationProperties.getProperty("stream.name", DEFAULT_CT_STREAM);
        return getFlinkKinesisConsumer(applicationProperties, companyTagStream);
    }

    private static FlinkKinesisConsumer<ObjectNode> createUTSource(Properties applicationProperties) {
        String userTagStream = applicationProperties.getProperty("stream.name", DEFAULT_UT_STREAM);
        return getFlinkKinesisConsumer(applicationProperties, userTagStream);
    }

    private static FlinkKinesisConsumer<ObjectNode> getFlinkKinesisConsumer(Properties applicationProperties,
                                                                            String streamName) {

        if(applicationProperties.getProperty("EnvName","local").equalsIgnoreCase("local")) {
            applicationProperties.setProperty(AWSConfigConstants.AWS_ENDPOINT, DEFAULT_AWS_ENDPOINT_URL);
        }

        return new FlinkKinesisConsumer<>(
                streamName,
                new JsonNodeDeserializationSchema(),
                applicationProperties);
    }

    private static KinesisStreamsSink<CompanyUserTagResult> createSink(Properties applicationProperties) {
        if(applicationProperties.getProperty("EnvName","local").equalsIgnoreCase("local")) {
            applicationProperties.setProperty(AWSConfigConstants.AWS_ENDPOINT, DEFAULT_AWS_ENDPOINT_URL);
        }

        return KinesisStreamsSink.<CompanyUserTagResult>builder()
                .setKinesisClientProperties(applicationProperties)
                .setSerializationSchema((SerializationSchema<CompanyUserTagResult>) element -> {
                    ObjectMapper mapper = new ObjectMapper();
                    byte[] output;
                    try {
                        output = mapper.writeValueAsString(element).getBytes();
                    } catch (Exception e) {
                        output = "".getBytes();
                    }
                    return output;
                })
                .setStreamName(applicationProperties.getProperty("stream.name", DEFAULT_OUTPUT_STREAM))
                .setPartitionKeyGenerator(element -> String.valueOf(element.hashCode()))
                .build();
    }


    public static void main(String[] args) throws Exception {
        // Set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Load application parameters
        final Map<String, Properties> applicationProperties = env instanceof LocalStreamEnvironment
                ? KinesisAnalyticsRuntime.getApplicationProperties(
                "src/main/resources/application_properties_file"
        ) : KinesisAnalyticsRuntime.getApplicationProperties();

        SourceFunction<ObjectNode> companyTagSource = createCTSource(applicationProperties.get("FirstSourceStream"));
        DataStream<ObjectNode> companyTagInput = env.addSource(companyTagSource, "CompanyTag Source");

        DataStream<CompanyTag> companyTagStream = companyTagInput
                .map((ObjectNode object) -> {
                    //For debugging input
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(object.toString(), CompanyTag.class);
                });


        SourceFunction<ObjectNode> userTagSource = createUTSource(applicationProperties.get("SecondSourceStream"));
        DataStream<ObjectNode> userTagInput = env.addSource(userTagSource, "UserTag Source");

        DataStream<UserTag> userTagStream = userTagInput
                .map((ObjectNode object) -> {
                    //For debugging input
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(object.toString(), UserTag.class);
                });

        companyTagStream.filter(companyTag -> companyTag.getData() != null)
                .join(userTagStream.filter(userTag -> userTag.getData() != null))
                .where(CompanyTag ->CompanyTag.getData().getId())
                .equalTo(UserTag -> UserTag.getData().getCompanyTagId())
                .window(SlidingProcessingTimeWindows.of(Time.seconds(5),Time.seconds(2)))
                .apply((JoinFunction<CompanyTag, UserTag, CompanyUserTagResult>) (companyTag, userTag) ->
                        new CompanyUserTagResult(userTag.getData().getCompanyTagId(), userTag.getData().getUserId(),
                                userTag.getData().getDeleted(),
                                companyTag.getData().getId(), companyTag.getData().getTag(),
                                companyTag.getData().getValue(), companyTag.getData().getDeleted(),
                                companyTag.getData().getCompanyTagLabelId(), companyTag.getData().getCompanyId()))
                .sinkTo(createSink(applicationProperties.get("SinkStream")));

        env.execute("Flink streaming Java API skeleton");
    }
}
