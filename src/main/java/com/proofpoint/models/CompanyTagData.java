package com.proofpoint.models;

import java.sql.Timestamp;

public class CompanyTagData {
    private long id;
    private String tagUUID;
    private long companyId;
    private String tag;
    private String value;
    private String dynamic;
    private String formula;
    private Timestamp version;
    private Timestamp created;
    private Timestamp modified;
    private Timestamp deleted;
    private int filterable;
    private int companyTagLabelId;

    public String getTagUUID() {
        return tagUUID;
    }

    public void setTagUUID(String tagUUID) {
        this.tagUUID = tagUUID;
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

    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

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

    public int getFilterable() {
        return filterable;
    }

    public void setFilterable(int filterable) {
        this.filterable = filterable;
    }

    public int getCompanyTagLabelId() {
        return companyTagLabelId;
    }

    public void setCompanyTagLabelId(int companyTagLabelId) {
        this.companyTagLabelId = companyTagLabelId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
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
}
