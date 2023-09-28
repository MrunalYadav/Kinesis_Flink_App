package com.proofpoint.models;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class CompanyTag {

    private Metadata metadata;
    private CompanyTagData companyTagData;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public CompanyTagData getData() {
        return companyTagData;
    }

    public void setData(CompanyTagData companyTagData) {
        this.companyTagData = companyTagData;
    }




}

