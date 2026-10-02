package com.pigmyMobileApp.model.sledger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "sledger_user")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SledgerUser {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "account_number")
    private String accountNumber;


    @Column(name = "current_balance")
    private long currentBalance;

    @Column(name = "last_deposit_date")
    private String lastDepositDate;

    @Column
    private String schemeId;

    @Column
    private Integer agentCode;

    @Column
    private String bankCode;

    @Column
    private String mobilenumber;

    @Column
    private String branchCode;
}
