package com.pigmyMobileApp.serviceactivators;

import com.pigmyMobileApp.model.AgentBankDTO;
import com.pigmyMobileApp.model.AgentBankProjection;
import com.pigmyMobileApp.model.AgentLogin;
import com.pigmyMobileApp.model.AuthenticateMeProjection;
import com.pigmyMobileApp.repository.AgentRepo;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component("agentLoginService")
public class AgentLoginService {


    @Autowired
    private AgentRepo agentLoginRepo;

    public boolean validate(String mobileNumber, String password, Exchange e) {
        AgentBankProjection agentLogin = agentLoginRepo.findByPhone(mobileNumber).orElseThrow(()-> new RuntimeException("Agents mobile number not found"));
        e.setProperty("bankCode",agentLogin.getBankCode());
        e.setProperty("agentCode",agentLogin.getAgentCode());
        e.setProperty("agentName",agentLogin.getagentName ());
        e.setProperty("bankName",agentLogin.getBankName());
        e.setProperty("phoneNumber",mobileNumber);
        e.setProperty("limitAmount", agentLogin.getlimitAmount());
        e.setProperty("graceDays", agentLogin.getGraceDays());
        e.setProperty("lastDepositDate", agentLogin.getLastDepositDate() != null ? agentLogin.getLastDepositDate().toString() : "No deposits yet");
        return agentLogin != null && agentLogin.getPassword().equals(password) && agentLogin.getStatus().equalsIgnoreCase("active");
    }


    public void authenticateAgent(@Header("phoneNumber") String phoneNumber,Exchange exchange)
    {
            AuthenticateMeProjection authenticateMeProjection= agentLoginRepo.fetchAgentDetails(phoneNumber);

            boolean isAgentRevoked=false;

            if(authenticateMeProjection==null)
            {
                throw new RuntimeException("Agent not found");
            }
            else
            {
               long limitAmount= authenticateMeProjection.getlimitAmount();
               int graceDays= authenticateMeProjection.getGraceDays();
               String lastDepositDate= authenticateMeProjection.getLastDepositDate().toString();
               String refreshToken= authenticateMeProjection.getRefreshToken();

               if(refreshToken==null || refreshToken.isEmpty())
               {
                    isAgentRevoked=true;
               }
               else
               {
                isAgentRevoked=false;
               }

                exchange.getIn().setBody(Map.of(
                        "limitAmount", limitAmount,
                        "graceDays", graceDays,
                        "lastDepositDate", lastDepositDate,
                        "isAgentRevoked", isAgentRevoked));

            }

    }
}
