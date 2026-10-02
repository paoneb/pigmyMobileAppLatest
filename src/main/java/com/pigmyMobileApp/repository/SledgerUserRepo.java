package com.pigmyMobileApp.repository;


import com.pigmyMobileApp.model.sledger.SledgerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SledgerUserRepo extends JpaRepository<SledgerUser, Long> {

    @Query("SELECT u FROM SledgerUser u  WHERE u.agentCode = :agentCode AND u.bankCode = :bankCode")
    List<SledgerUser> findUsersByAgentCode_bankCode(@Param("agentCode") Integer agentCode,
                                                    @Param("bankCode") String bankCode);
}
