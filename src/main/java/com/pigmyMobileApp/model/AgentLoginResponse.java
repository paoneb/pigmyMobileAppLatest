package com.pigmyMobileApp.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgentLoginResponse {
    private String agentName;
    private Integer agentCode;
    private String bankCode;
    private String token;
}
