package com.pigmyMobileApp.repository;

import com.pigmyMobileApp.model.AgentBankDTO;
import com.pigmyMobileApp.model.AgentBankProjection;
import com.pigmyMobileApp.model.AgentLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AgentRepo extends JpaRepository<AgentLogin,Long> {

    @Query(value = "SELECT ad.bank_name AS bankName, ad.bank_code AS bankCode, " +
            "a.agent_code AS agentCode, a.agent_name AS agentName, " +
            "a.password AS password, a.status AS status, " +
            "a.limit_amount AS limitAmount, a.grace_days AS graceDays, " +
            "(SELECT CAST(MAX(adp.deposit_date) AS DATE) " +
            " FROM agents_deposit adp " +
            " WHERE adp.bank_code = a.bank_code " +
            "   AND adp.agent_code = a.agent_code) AS lastDepositDate " +
            "FROM admin_web ad " +
            "JOIN agents a ON a.bank_code = ad.bank_code " +
            "WHERE a.phone = :phone",
            nativeQuery = true)
    Optional<AgentBankProjection> findByPhone(@Param("phone") String phone);
}
