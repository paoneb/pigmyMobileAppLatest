package com.pigmyMobileApp.repository;

import com.pigmyMobileApp.model.User;
import com.pigmyMobileApp.model.peocit.PeocitUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PeocitUserRepo extends JpaRepository<PeocitUser,Long> {



   // List<User> findByAgents_AgentCodeAndAgents_BankCode(Integer agentCode,String bankCode);

    @Query("SELECT u FROM PeocitUser u  WHERE u.agentCode = :agentCode AND u.bankCode = :bankCode")
     List<PeocitUser> findUsersByAgentCode_bankCode(@Param("agentCode") Integer agentCode,
                                @Param("bankCode") String bankCode);


}
