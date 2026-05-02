package com.pigmyMobileApp.serviceactivators;

import com.pigmyMobileApp.model.AgentBankDTO;
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
        AgentBankDTO agentLogin = agentLoginRepo.findByPhone(mobileNumber).orElseThrow(()-> new RuntimeException("Agents mobile number not found"));
        e.setProperty("bankCode",agentLogin.getBankCode());
        e.setProperty("agentCode",agentLogin.getAgentCode());
        e.setProperty("agentName",agentLogin.getname());
        e.setProperty("bankName",agentLogin.getBankName());
        return agentLogin != null && agentLogin.getPassword().equals(password) && agentLogin.getstatus().equalsIgnoreCase("active");
    }
}
