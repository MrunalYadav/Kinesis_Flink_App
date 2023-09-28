package com.proofpoint.models;

import java.sql.Timestamp;

public class UserTagData {
    private long id;
    private String userId;
    private String companyTagUUID;
    private Timestamp version;
    private Timestamp created;
    private Timestamp modified;
    private Timestamp deleted;
    private long companyTagId;

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    public void setDeleted(Timestamp deleted) {
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getVersion() {
        return version;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getModified() {
        return modified;
    }

    public Timestamp getDeleted() {
        return deleted;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyTagUUID() {
        return companyTagUUID;
    }

    public void setCompanyTagUUID(String companyTagUUID) {
        this.companyTagUUID = companyTagUUID;
    }

    public long getCompanyTagId() {
        return companyTagId;
    }

    public void setCompanyTagId(long companyTagId) {
        this.companyTagId = companyTagId;
    }
}
