package com.proofpoint;

import com.amazonaws.services.kinesisanalytics.runtime.KinesisAnalyticsRuntime;
import com.proofpoint.models.Account;
import com.proofpoint.models.CompanyAccountResult;
import com.proofpoint.models.Companys;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.common.serialization.SerializationSchema;
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

import java.util.Map;
import java.util.Properties;

/**
 * A basic Flink Java application to run on Amazon Managed Service for Apache Flink,
 * with Kinesis Data Streams as source and sink.
 */
public class CrossAccountStreamingJob {

    private static final String DEFAULT_CT_STREAM = "CompanyTagStream";
    private static final String DEFAULT_UT_STREAM = "UserTagStream";
    private static final String DEFAULT_OUTPUT_STREAM = "CompanyUserTagResultStream";
    private static final String DEFAULT_AWS_ENDPOINT_URL = "http://localhost:4566";

    private static FlinkKinesisConsumer<ObjectNode> createCompanysSource(Properties applicationProperties) {

        String companyTagStream = applicationProperties.getProperty("stream.name", DEFAULT_CT_STREAM);
        return getFlinkKinesisConsumer(applicationProperties, companyTagStream);
    }

    private static FlinkKinesisConsumer<ObjectNode> createAccountSource(Properties applicationProperties) {
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

    private static KinesisStreamsSink<CompanyAccountResult> createSink(Properties applicationProperties) {
        if(applicationProperties.getProperty("EnvName","local").equalsIgnoreCase("local")) {
            applicationProperties.setProperty(AWSConfigConstants.AWS_ENDPOINT, DEFAULT_AWS_ENDPOINT_URL);
        }

        return KinesisStreamsSink.<CompanyAccountResult>builder()
                .setKinesisClientProperties(applicationProperties)
                .setSerializationSchema((SerializationSchema<CompanyAccountResult>) element -> {
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
                "/Users/myadav/PycharmProjects/amazon-managed-service-for-apache-flink-examples/" +
                        "java/GettingStarted/application_properties_file"
        ) : KinesisAnalyticsRuntime.getApplicationProperties();

        SourceFunction<ObjectNode> companyTagSource = createCompanysSource(applicationProperties.get("FirstSourceStream"));
        DataStream<ObjectNode> companyTagInput = env.addSource(companyTagSource, "Company Source");

        DataStream<Companys> companyTagStream = companyTagInput
                .map((ObjectNode object) -> {
                    //For debugging input
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(object.toString(), Companys.class);
                });


        SourceFunction<ObjectNode> userTagSource = createAccountSource(applicationProperties.get("SecondSourceStream"));
        DataStream<ObjectNode> userTagInput = env.addSource(userTagSource, "Account Source");

        DataStream<Account> userTagStream = userTagInput
                .map((ObjectNode object) -> {
                    //For debugging input
                    ObjectMapper mapper = new ObjectMapper();
                    return mapper.readValue(object.toString(), Account.class);
                });

        companyTagStream.filter(companys -> companys.getData() != null)
                .join(userTagStream.filter(account -> account.getData() != null))
                .where(companys ->companys.getData().getId())
                .equalTo(account -> account.getData().getPartnerAccountId())
                .window(SlidingProcessingTimeWindows.of(Time.seconds(5),Time.seconds(2)))
                .apply((JoinFunction<Companys, Account, CompanyAccountResult>) (company, account) ->
                        new CompanyAccountResult(company.getData().getId(),company.getData().getName(),
                                company.getData().getCompanyTypeId(), company.getData().getAccountTypeId(),
                                company.getData().getDeleted(),account.getData().getName(),
                                account.getData().getDeleted(), account.getData().getEnabled(),
                                account.getData().getCompanyCategory(), account.getData().getAccountType()))
                .sinkTo(createSink(applicationProperties.get("SinkStream")));

        env.execute("Flink streaming Java API skeleton");
    }
}
