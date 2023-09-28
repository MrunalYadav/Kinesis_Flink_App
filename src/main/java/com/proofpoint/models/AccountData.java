package com.proofpoint.models;

import java.sql.Timestamp;

public class AccountData {
    private long id;
    private String name;
    private int createdBy;
    private Timestamp created;
    private Timestamp modified;
    private Timestamp deleted;
    private int allocatedTargets;
    private int enabled;
    private int twoFactorEnabled;
    private Timestamp expirationDate;
    private String permittedDomains;
    private String logo;
    private String companyCategory;
    private String accountType;
    private int partnerId;
    private long partnerAccountId;
    private int collectVulnerabilities;
    private int anonymize;
    private String vanityDomains;
    private int platformUsbEnabled;
    private int platformSmsEnabled;
    private int partnerAccountUuid;
    private String partnerProvisioningType;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    public Timestamp getDeleted() {
        return deleted;
    }

    public void setDeleted(Timestamp deleted) {
        this.deleted = deleted;
    }

    public int getAllocatedTargets() {
        return allocatedTargets;
    }

    public void setAllocatedTargets(int allocatedTargets) {
        this.allocatedTargets = allocatedTargets;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public int getTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public void setTwoFactorEnabled(int twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getPermittedDomains() {
        return permittedDomains;
    }

    public void setPermittedDomains(String permittedDomains) {
        this.permittedDomains = permittedDomains;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCompanyCategory() {
        return companyCategory;
    }

    public void setCompanyCategory(String companyCategory) {
        this.companyCategory = companyCategory;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public long getPartnerAccountId() {
        return partnerAccountId;
    }

    public void setPartnerAccountId(long partnerAccountId) {
        this.partnerAccountId = partnerAccountId;
    }

    public int getCollectVulnerabilities() {
        return collectVulnerabilities;
    }

    public void setCollectVulnerabilities(int collectVulnerabilities) {
        this.collectVulnerabilities = collectVulnerabilities;
    }

    public int getAnonymize() {
        return anonymize;
    }

    public void setAnonymize(int anonymize) {
        this.anonymize = anonymize;
    }

    public String getVanityDomains() {
        return vanityDomains;
    }

    public void setVanityDomains(String vanityDomains) {
        this.vanityDomains = vanityDomains;
    }

    public int getPlatformUsbEnabled() {
        return platformUsbEnabled;
    }

    public void setPlatformUsbEnabled(int platformUsbEnabled) {
        this.platformUsbEnabled = platformUsbEnabled;
    }

    public int getPlatformSmsEnabled() {
        return platformSmsEnabled;
    }

    public void setPlatformSmsEnabled(int platformSmsEnabled) {
        this.platformSmsEnabled = platformSmsEnabled;
    }

    public int getPartnerAccountUuid() {
        return partnerAccountUuid;
    }

    public void setPartnerAccountUuid(int partnerAccountUuid) {
        this.partnerAccountUuid = partnerAccountUuid;
    }

    public String getPartnerProvisioningType() {
        return partnerProvisioningType;
    }

    public void setPartnerProvisioningType(String partnerProvisioningType) {
        this.partnerProvisioningType = partnerProvisioningType;
    }
}
