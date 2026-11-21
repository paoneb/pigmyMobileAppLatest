package com.pigmyMobileApp.model;


import com.pigmyMobileApp.model.peocit.SchemesDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

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
    private int offlineTransactionLimit;
    private String refreshToken;
    private String accessToken;
    private List<SchemesDTO> schemes;
    private String bankType;


}
