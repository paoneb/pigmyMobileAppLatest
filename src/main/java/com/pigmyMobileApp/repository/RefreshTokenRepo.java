package com.pigmyMobileApp.repository;

import com.pigmyMobileApp.model.AgentLogin;
import com.pigmyMobileApp.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Long> {
    Optional<Object> findByMobileNumber(String phoneNumber);

    Optional<RefreshToken> findByToken(String refreshToken);
}
