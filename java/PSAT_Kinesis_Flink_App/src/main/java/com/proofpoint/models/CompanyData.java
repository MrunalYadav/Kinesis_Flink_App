package com.proofpoint.models;

import java.sql.Timestamp;

public class CompanyData {
    private long id;
    private String name;
    private int authMethodId;
    private Timestamp created;
    private Timestamp modified;
    private Timestamp deleted;
    private int accountTypeId;
    private Timestamp version;
    private String abbreviation;
    private String locale;
    private String requiresLicenseAgreement;
    private String multiLanguage;
    private int companyTypeId;
    private int oldDomain;
    private String timeZone;
    private String migrated;
    private String guruId;
    private String companyLogo;
    private String companyUUID;
    private String encryptionSeed;
    private String endUserLogoId;

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

    public int getAuthMethodId() {
        return authMethodId;
    }

    public void setAuthMethodId(int authMethodId) {
        this.authMethodId = authMethodId;
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

    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public Timestamp getVersion() {
        return version;
    }

    public void setVersion(Timestamp version) {
        this.version = version;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getRequiresLicenseAgreement() {
        return requiresLicenseAgreement;
    }

    public void setRequiresLicenseAgreement(String requiresLicenseAgreement) {
        this.requiresLicenseAgreement = requiresLicenseAgreement;
    }

    public String getMultiLanguage() {
        return multiLanguage;
    }

    public void setMultiLanguage(String multiLanguage) {
        this.multiLanguage = multiLanguage;
    }

    public int getCompanyTypeId() {
        return companyTypeId;
    }

    public void setCompanyTypeId(int companyTypeId) {
        this.companyTypeId = companyTypeId;
    }

    public int getOldDomain() {
        return oldDomain;
    }

    public void setOldDomain(int oldDomain) {
        this.oldDomain = oldDomain;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getMigrated() {
        return migrated;
    }

    public void setMigrated(String migrated) {
        this.migrated = migrated;
    }

    public String getGuruId() {
        return guruId;
    }

    public void setGuruId(String guruId) {
        this.guruId = guruId;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyUUID() {
        return companyUUID;
    }

    public void setCompanyUUID(String companyUUID) {
        this.companyUUID = companyUUID;
    }

    public String getEncryptionSeed() {
        return encryptionSeed;
    }

    public void setEncryptionSeed(String encryptionSeed) {
        this.encryptionSeed = encryptionSeed;
    }

    public String getEndUserLogoId() {
        return endUserLogoId;
    }

    public void setEndUserLogoId(String endUserLogoId) {
        this.endUserLogoId = endUserLogoId;
    }
}
