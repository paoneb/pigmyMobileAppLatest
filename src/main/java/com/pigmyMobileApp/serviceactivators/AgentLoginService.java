package com.pigmyMobileApp.serviceactivators;

import com.pigmyMobileApp.model.AgentBankProjection;
import com.pigmyMobileApp.model.AuthenticateMeProjection;
import com.pigmyMobileApp.repository.AgentRepo;
import com.pigmyMobileApp.repository.SchemeMappingRepo;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component("agentLoginService")
@RequiredArgsConstructor
public class AgentLoginService {

    private final AgentRepo agentLoginRepo;

    private final SchemeMappingRepo schemeMappingRepo;

    private final Logger LOGGER = LoggerFactory.getLogger(AgentLoginService.class);

    public boolean validate(String mobileNumber, String password, Exchange e) {
        AgentBankProjection agentLogin = agentLoginRepo.findByPhone(mobileNumber).orElseThrow(() -> new RuntimeException("Agents mobile number not found"));
        e.setProperty("bankCode", agentLogin.getBankCode());
        e.setProperty("agentCode", agentLogin.getAgentCode());
        e.setProperty("agentName", agentLogin.getagentName());
        e.setProperty("bankName", agentLogin.getBankName());
        e.setProperty("phoneNumber", mobileNumber);
        e.setProperty("limitAmount", agentLogin.getlimitAmount());
        e.setProperty("graceDays", agentLogin.getGraceDays());
        e.setProperty("offlineTransactionLimit", agentLogin.getOfflineTransactionLimit());
        e.setProperty("lastDepositDate", agentLogin.getLastDepositDate() != null ? agentLogin.getLastDepositDate().toString() : "No deposits yet");
        e.setProperty("schemes", schemeMappingRepo.findByBankCode(agentLogin.getBankCode()));
        e.setProperty("bankType", agentLogin.getbankType());
        return agentLogin != null && agentLogin.getPassword().equals(password) && agentLogin.getStatus().equalsIgnoreCase("active");
    }


    public void authenticateAgent(@Header("phoneNumber") String phoneNumber, @Header("refresh_token") String refreshToken, Exchange exchange) {

        LOGGER.info("Authenticating agent with phone number: {}", phoneNumber);

        AuthenticateMeProjection authenticateMeProjection = agentLoginRepo.fetchAgentDetails(phoneNumber);

        boolean isAgentRevoked = false;

        if (authenticateMeProjection == null) {
            throw new RuntimeException("Agent not found");
        } else {
            long limitAmount = authenticateMeProjection.getlimitAmount();
            int graceDays = authenticateMeProjection.getGraceDays();
            int offlineTransactionLimit = authenticateMeProjection.getOfflineTransactionLimit();
            String lastDepositDate = authenticateMeProjection.getLastDepositDate().toString();
            String refreshTokenFromDB = authenticateMeProjection.getRefreshToken();

            if (refreshToken == null || refreshToken.isEmpty() || !Objects.equals(refreshTokenFromDB, refreshToken)) {
                isAgentRevoked = true;
            } else {
                isAgentRevoked = false;
            }

            exchange.getIn().setBody(Map.of(
                    "limitAmount", limitAmount,
                    "graceDays", graceDays,
                    "offlineTransactionLimit", offlineTransactionLimit,
                    "lastDepositDate", lastDepositDate,
                    "isAgentRevoked", isAgentRevoked));

        }

    }
}
