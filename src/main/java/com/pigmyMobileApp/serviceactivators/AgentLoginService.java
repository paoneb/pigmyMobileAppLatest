package com.pigmyMobileApp.serviceactivators;

import com.pigmyMobileApp.model.AgentLogin;
import com.pigmyMobileApp.repository.AgentRepo;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgentLoginService {


    @Autowired
    private AgentRepo agentLoginRepo;

    public boolean validate(String mobileNumber, String password, Exchange e) {
        AgentLogin agentLogin = agentLoginRepo.findByPhone(mobileNumber);
        e.setProperty("bankCode",agentLogin.getBankCode());
        e.setProperty("agentCode",agentLogin.getAgentCode());
        e.setProperty("agentName",agentLogin.getName());
        return agentLogin != null && agentLogin.getPassword().equals(password) && agentLogin.getStatus().equalsIgnoreCase("active");
    }
}
