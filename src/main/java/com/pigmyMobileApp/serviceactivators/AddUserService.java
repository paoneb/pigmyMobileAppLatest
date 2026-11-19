package com.pigmyMobileApp.serviceactivators;


import com.pigmyMobileApp.model.User;
import com.pigmyMobileApp.model.peocit.PeocitUser;
import com.pigmyMobileApp.model.sledger.SledgerUser;
import com.pigmyMobileApp.repository.PeocitUserRepo;
import com.pigmyMobileApp.repository.SledgerUserRepo;
import com.pigmyMobileApp.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Header;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("addUserService")
@RequiredArgsConstructor
public class AddUserService {


    private final UserRepo userRepo;
    private final PeocitUserRepo peocitUserRepo;
    private final SledgerUserRepo sledgerUserRepo;


    public List<User> fetchCustomers(@Header("agentCode") final Integer agentCode, @Header("bankCode") final String bankCode) {
        if (agentCode != null) {
            List<User> users = userRepo.findUsersByAgentCode_bankCode(agentCode, bankCode);
            return users;

        } else {
            throw new RuntimeException("Agent not found");
        }


    }

    public List<PeocitUser> fetchCustomersPeocit(@Header("agentCode") final Integer agentCode, @Header("bankCode") final String bankCode) {
        if (agentCode != null) {
            List<PeocitUser> peousers = peocitUserRepo.findUsersByAgentCode_bankCode(agentCode, bankCode);
            return peousers;
        } else
            throw new RuntimeException("Agent not found");

    }

    public List<SledgerUser> fetchCustomersSledger(@Header("agentCode") final Integer agentCode, @Header("bankCode") final String bankCode) {
        if (agentCode != null) {
            List<SledgerUser> susers = sledgerUserRepo.findUsersByAgentCode_bankCode(agentCode, bankCode);
            return susers;

        } else {
            throw new RuntimeException("Agent not found");
        }
    }
}

