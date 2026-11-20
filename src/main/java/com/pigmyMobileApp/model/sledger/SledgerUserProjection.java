package com.pigmyMobileApp.model.sledger;

public interface SledgerUserProjection {

    Long getUserId();
    String getCustomerName();
    String getAccountNumber();
    String getLastDepositDate();
    String getSchemeId();
    String getMobilenumber();
    String getBranchCode();
    Integer getAgentCode();
    String getBankCode();
    Long getCurrentBalance();
}