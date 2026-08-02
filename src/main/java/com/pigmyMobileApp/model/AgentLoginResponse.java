package com.pigmyMobileApp.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgentLoginResponse {
    private String agentName;
    private Integer agentCode;
    private String bankCode;
    private String bankName;
    private String phoneNumber;
    private String lastDepositDate;
    private long  limitAmount;
    private int graceDays;
    private String refreshToken;
    private String accessToken;


}
