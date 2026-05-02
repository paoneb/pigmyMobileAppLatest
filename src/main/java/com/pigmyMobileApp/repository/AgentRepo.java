package com.pigmyMobileApp.repository;

import com.pigmyMobileApp.model.AgentBankDTO;
import com.pigmyMobileApp.model.AgentLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AgentRepo extends JpaRepository<AgentLogin,Long> {

    @Query(value = "SELECT ad.bank_name AS bankName, ad.bank_code AS bankCode, " +
            "a.agent_code AS agentCode, a.agent_name AS agentName, " +
            "a.password AS password, a.status AS status " +
            "FROM admin_web ad " +
            "JOIN agents a ON a.bank_code = ad.bank_code " +
            "WHERE a.phone = :phone", nativeQuery = true)
    Optional<AgentBankDTO> findByPhone(@Param("phone") String phone);
}
