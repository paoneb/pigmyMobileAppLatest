package com.pigmyMobileApp.serviceactivators;


import com.pigmyMobileApp.config.JwtUtil;
import com.pigmyMobileApp.model.RefreshToken;
import com.pigmyMobileApp.repository.RefreshTokenRepo;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component("refreshTokenService")
@RequiredArgsConstructor
public class RefreshTokenService {


    private final RefreshTokenRepo refreshTokenRepo;

    private final JwtUtil jwtUtil;


    public String generateRefreshToken(final Exchange e) {
        Integer agentCode = e.getProperty("agentCode", Integer.class);
        String bankCode = e.getProperty("bankCode", String.class);
        String bankName = e.getProperty("bankName", String.class);
        String phoneNumber = e.getProperty("phoneNumber", String.class);

        return refreshTokenRepo.findByMobileNumber(phoneNumber)
                .map(existingToken -> {
                    // If a refresh token already exists, throw an exception
                    throw new RuntimeException("Refresh token already exists for mobile number: " + phoneNumber);
                })
                .orElseGet(() -> {
                    // If no refresh token exists, generate and save one
                    Instant expiryDate = jwtUtil.getRefreshTokenExpiryDate();
                    String newToken = jwtUtil.generateRefreshToken(phoneNumber, expiryDate);


                    RefreshToken tokenEntity = new RefreshToken();
                    tokenEntity.setMobileNumber(phoneNumber);
                    tokenEntity.setBankCode(bankCode);
                    tokenEntity.setAgentCode(agentCode);
                    tokenEntity.setBankName(bankName);
                    tokenEntity.setExpiryDate(expiryDate);
                    tokenEntity.setToken(newToken);

                    refreshTokenRepo.save(tokenEntity);

                    return newToken; // return the token string directly
                }).toString();


    }

    public boolean validateRefreshToken(String refreshToken, String mobileNumber) {
        RefreshToken tokenEntity = refreshTokenRepo.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        // Check if the provided refresh token matches the stored one and is not expired
        return tokenEntity.getToken().equals(refreshToken) && Instant.now().isBefore(tokenEntity.getExpiryDate());

    }

    public void reGenerateRefreshToken(String refreshToken, String mobileNumber, Exchange exchange) {
        RefreshToken tokenEntity = refreshTokenRepo.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token not found "));

        // Generate a new refresh token
        String newAccessToken = jwtUtil.generateToken(mobileNumber);
        Instant expiryDate = jwtUtil.getRefreshTokenExpiryDate();
        String newRefreshToken = jwtUtil.generateRefreshToken(mobileNumber, expiryDate);


        // Update the existing token entity
        tokenEntity.setToken(newRefreshToken);
        tokenEntity.setExpiryDate(expiryDate);

        refreshTokenRepo.save(tokenEntity);

        exchange.getIn().setBody(Map.of(
                "accessToken", newAccessToken,
                "refreshToken", newRefreshToken));

    }
}
