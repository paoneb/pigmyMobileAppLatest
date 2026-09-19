package com.pigmyMobileApp.repository;


import com.pigmyMobileApp.model.Transaction;
import com.pigmyMobileApp.model.TransactionSummaryProjection;
import com.pigmyMobileApp.model.peocit.PeocitTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PeocitTransactionRepo extends JpaRepository<PeocitTransaction,Long> {

    @Query("SELECT c FROM PeocitTransaction c WHERE c.agentCode = :agentCode AND c.bankCode = :bankCode AND c.collectedDate = :collectedDate AND c.status = 'C'")
    List<PeocitTransaction> findByAgentCodeAndBankCodeAndCollectedDateAndstatus(Integer agentCode, String bankCode,LocalDate collectedDate);


    @Query(value = "SELECT COALESCE(SUM(t.collected_amount), 0) AS totalCollected, " +
            "COUNT(*) AS transactionCount " +
            "FROM peocit_transactions t " +
            "WHERE t.agent_code = :agentCode " +
            "AND t.bank_code = :bankCode " +
            "AND UPPER(t.status) = 'COLLECTED' " +
            "AND (t.agent_deposited_date IS NULL OR t.collected_date >= t.agent_deposited_date) AND t.collected_date <= CURRENT_DATE",
            nativeQuery = true)
    TransactionSummaryProjection findTransactionSummary(@Param("agentCode") int agentCode,
                                                        @Param("bankCode") String bankCode);

}
