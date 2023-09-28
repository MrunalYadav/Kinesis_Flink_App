package com.proofpoint.models;

import java.sql.Timestamp;

public class UserTag {

    private Metadata metadata;
    private UserTagData data;

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public UserTagData getData() {
        return data;
    }

    public void setData(UserTagData data) {
        this.data = data;
    }




}

