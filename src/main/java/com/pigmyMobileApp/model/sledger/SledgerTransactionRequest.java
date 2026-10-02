package com.pigmyMobileApp.model.sledger;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SledgerTransactionRequest {

    private long collectedAmount;
    private String schemeId;
    private String collectiontype;
    private Integer agentCode;
    private String agentName;
    private String bankCode;
    private long userId;
    private String customerName;
    private String accountNumber;
    private String transactionId;
    private String branchCode;
}
