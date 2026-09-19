package com.pigmyMobileApp.model;

import java.time.LocalDate;

public interface AgentBankProjection {
    String getBankName();
    String getBankCode();
    String getbankType();
    Integer getAgentCode();
    String getagentName ();
    String getPassword();
    String getStatus();
    Long getlimitAmount();
    int getGraceDays();
    LocalDate getLastDepositDate();


}
