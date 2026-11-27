package com.pigmyMobileApp.model;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FetchTransactionResponse {

    private Long trasactionId;
    private String accountNumber;
    private String customerName;
    private long collectedAmount;


}
