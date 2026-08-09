package com.pigmyMobileApp.repository;


import com.pigmyMobileApp.model.Transaction;
import com.pigmyMobileApp.model.TransactionSummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction,Long> {

    @Query("SELECT c FROM Transaction c WHERE c.agentCode = :agentCode AND c.bankCode = :bankCode AND c.collectedDate = :collectedDate AND c.status = 'C'")
    List<Transaction> findByAgentCodeAndBankCodeAndCollectedDateAndstatus(Integer agentCode, String bankCode,LocalDate collectedDate);


    @Query(value = "SELECT COALESCE(SUM(t.collected_amount), 0) AS totalCollected, " +
            "COUNT(*) AS transactionCount " +
            "FROM transactions t " +
            "WHERE t.agent_code = :agentCode " +
            "AND t.bank_code = :bankCode " +
            "AND t.status = 'Collected' " +
            "AND t.collected_date >= CURRENT_DATE - INTERVAL :graceDays DAY",
            nativeQuery = true)
    TransactionSummaryProjection findTransactionSummary(@Param("agentCode") int agentCode,
                                                        @Param("bankCode") String bankCode,
                                                        @Param("graceDays") int graceDays);

}
