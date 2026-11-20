package com.pigmyMobileApp.repository;


import com.pigmyMobileApp.model.sledger.SledgerUser;
import com.pigmyMobileApp.model.sledger.SledgerUserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SledgerUserRepo extends JpaRepository<SledgerUser, Long> {

    @Query(value = """
            SELECT
                u.user_id AS userId,
                u.customer_name AS customerName,
                u.account_number AS accountNumber,
                u.last_deposit_date AS lastDepositDate,
                u.scheme_id AS schemeId,
                u.mobilenumber AS mobilenumber,
                u.branch_code AS branchCode,
                u.agent_code AS agentCode,
                u.bank_code AS bankCode,
                u.current_balance + COALESCE(SUM(t.collected_amount),0) AS currentBalance
            FROM sledger_user u
            LEFT JOIN sledger_transactions t
                ON t.user_id = u.user_id
               AND t.status = 'Collected'
            WHERE u.agent_code = :agentCode
              AND u.bank_code = :bankCode
            GROUP BY
                u.user_id,
                u.customer_name,
                u.account_number,
                u.last_deposit_date,
                u.scheme_id,
                u.mobilenumber,
                u.branch_code,
                u.agent_code,
               u.bank_code,
                u.current_balance
            """, nativeQuery = true)
    List<SledgerUserProjection> findUsersByAgentCode_bankCode(@Param("agentCode") Integer agentCode,
                                                              @Param("bankCode") String bankCode);
}
