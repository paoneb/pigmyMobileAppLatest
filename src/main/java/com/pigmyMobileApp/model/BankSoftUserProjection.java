package com.pigmyMobileApp.model;

public interface BankSoftUserProjection {

    Long getUserId();
    String getCustomerName();
    String getAccountNumber();
    String getLastDepositDate();
    String getSchemeId();
    String getMobilenumber();
    Integer getAgentCode();
    String getBankCode();
    Long getCurrentBalance();
}