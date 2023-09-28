package com.proofpoint.models;

import java.sql.Timestamp;

public class CompanyUserTagResult {
    private long userTagId;
    private String userId ;
    private long companyTagId;
    private String tag;
    private String value;
    private Timestamp userTagDeleted;
    private Timestamp companyTagDeleted;
    private long companyTagLabelId;

    private long companyId;

    public long getUserTagId() {
        return userTagId;
    }

    public void setUserTagId(long userTagId) {
        this.userTagId = userTagId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getCompanyTagId() {
        return companyTagId;
    }

    public void setCompanyTagId(long companyTagId) {
        this.companyTagId = companyTagId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Timestamp getUserTagDeleted() {
        return userTagDeleted;
    }

    public void setUserTagDeleted(Timestamp userTagDeleted) {
        this.userTagDeleted = userTagDeleted;
    }

    public Timestamp getCompanyTagDeleted() {
        return companyTagDeleted;
    }

    public void setCompanyTagDeleted(Timestamp companyTagDeleted) {
        this.companyTagDeleted = companyTagDeleted;
    }

    public long getCompanyTagLabelId() {
        return companyTagLabelId;
    }

    public void setCompanyTagLabelId(long companyTagLabelId) {
        this.companyTagLabelId = companyTagLabelId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "Result{" +
                "userTagId=" + userTagId +
                ", userId='" + userId + '\'' +
                ", companyTagId=" + companyTagId +
                ", tag='" + tag + '\'' +
                ", value='" + value + '\'' +
                ", userTagDeleted=" + userTagDeleted +
                ", companyTagDeleted=" + companyTagDeleted +
                ", companyTagLabelId=" + companyTagLabelId +
                ", companyId=" + companyId +
                '}';
    }

    public CompanyUserTagResult(long userTagId, String userId, Timestamp userTagDeleted,
                                long companyTagId, String tag, String value, Timestamp companyTagDeleted,
                                long companyTagLabelId, long companyId) {
        this.userTagId = userTagId;
        this.userId = userId;
        this.companyTagId = companyTagId;
        this.tag = tag;
        this.value = value;
        this.userTagDeleted = userTagDeleted;
        this.companyTagDeleted = companyTagDeleted;
        this.companyTagLabelId = companyTagLabelId;
        this.companyId = companyId;
    }
}