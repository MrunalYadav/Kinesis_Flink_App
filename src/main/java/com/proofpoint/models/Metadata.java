package com.proofpoint.models;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty;

public class Metadata {
    private String timestamp;
    @JsonProperty("record-type")
    private String recordType;
    private String operation;
    @JsonProperty("partition-key-type")
    private String partitionKeyType;
    @JsonProperty(value = "partition-key-value")
    private String partitionKeyValue;
    @JsonProperty("schema-name")
    private String schemaName;
    @JsonProperty("table-name")
    private String tableName;
    @JsonProperty("transaction-id")
    private String transactionId;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getPartitionKeyType() {
        return partitionKeyType;
    }

    public void setPartitionKeyType(String partitionKeyType) {
        this.partitionKeyType = partitionKeyType;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPartitionKeyValue() {
        return partitionKeyValue;
    }

    public void setPartitionKeyValue(String partitionKeyValue) {
        this.partitionKeyValue = partitionKeyValue;
    }
}
