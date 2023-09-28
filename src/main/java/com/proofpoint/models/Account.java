package com.proofpoint.models;

import java.sql.Timestamp;

public class Account {

    private Metadata metadata;
    private AccountData data;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public AccountData getData() {
        return data;
    }

    public void setData(AccountData data) {
        this.data = data;
    }

}

