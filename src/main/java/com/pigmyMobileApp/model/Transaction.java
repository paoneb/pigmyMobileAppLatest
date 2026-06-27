package com.pigmyMobileApp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="transactions")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double collectedAmount;

    @Column
    private LocalDate collectedDate;

    @Column
    private String schemename;

    @Column
    private String schemeId;

    @Column
    private String collectiontype;

    @Column
    private String status;

    @Column
    private long agentDepositId;

    @Column
    private Integer agentCode;

    @Column
    private String bankCode;

    @Column
    private long userId;

    @Column
    private String customerName;

    @Column
    private Integer accountNumber;

    @Column
    private String transactionId;

    @Column
    private String agentname;
}
