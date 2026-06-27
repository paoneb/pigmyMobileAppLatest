package com.pigmyMobileApp.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class TransactionRequest {

    private Double collectedAmount;
    private String schemename;
    private String schemeId;
    private String collectiontype;
    private Integer agentCode;
    private String agentName;
    private String bankCode;
    private long userId;
    private String customerName;
    private Integer accountNumber;
    private String transactionId;
}
