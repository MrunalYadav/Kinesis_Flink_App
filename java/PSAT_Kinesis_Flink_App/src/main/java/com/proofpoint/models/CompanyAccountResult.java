package com.proofpoint.models;

import java.sql.Timestamp;

public class CompanyAccountResult {
    private long companyId;
    private String companyName;
    private int companyTypeId;
    private int accountTypeId;
    private Timestamp companyDeleted;
    private String accountName;
    private Timestamp accountDeleted;
    private int accountEnabled;
    private String companyCategory;
    private String accountType;


    public CompanyAccountResult(long companyId, String companyName, int companyTypeId, int accountTypeId,
                                Timestamp companyDeleted, String accountName, Timestamp accountDeleted,
                                int accountEnabled, String companyCategory, String accountType) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyTypeId = companyTypeId;
        this.accountTypeId = accountTypeId;
        this.companyDeleted = companyDeleted;
        this.accountName = accountName;
        this.accountDeleted = accountDeleted;
        this.accountEnabled = accountEnabled;
        this.companyCategory = companyCategory;
        this.accountType = accountType;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyTypeId() {
        return companyTypeId;
    }

    public void setCompanyTypeId(int companyTypeId) {
        this.companyTypeId = companyTypeId;
    }

    public int getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(int accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public Timestamp getCompanyDeleted() {
        return companyDeleted;
    }

    public void setCompanyDeleted(Timestamp companyDeleted) {
        this.companyDeleted = companyDeleted;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Timestamp getAccountDeleted() {
        return accountDeleted;
    }

    public void setAccountDeleted(Timestamp accountDeleted) {
        this.accountDeleted = accountDeleted;
    }

    public int getAccountEnabled() {
        return accountEnabled;
    }

    public void setAccountEnabled(int accountEnabled) {
        this.accountEnabled = accountEnabled;
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


}