package com.proofpoint.models;

import java.sql.Timestamp;

public class Companys {

    private Metadata metadata;
    private CompanyData data;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public CompanyData getData() {
        return data;
    }

    public void setData(CompanyData data) {
        this.data = data;
    }

}

