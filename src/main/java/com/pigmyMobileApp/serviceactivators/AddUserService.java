package com.pigmyMobileApp.serviceactivators;


import com.pigmyMobileApp.model.BankSoftUserProjection;
import com.pigmyMobileApp.model.peocit.PeocitUserProjection;
import com.pigmyMobileApp.model.sledger.SledgerUser;
import com.pigmyMobileApp.model.sledger.SledgerUserProjection;
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


    public List<BankSoftUserProjection> fetchCustomers(@Header("agentCode") final Integer agentCode, @Header("bankCode") final String bankCode) {
        if (agentCode != null) {
            List<BankSoftUserProjection> users = userRepo.findUsersByAgentCode_bankCode(agentCode, bankCode);
            return users;
        } else {
            throw new RuntimeException("Agent not found");
        }


    }

    public List<PeocitUserProjection> fetchCustomersPeocit(@Header("agentCode") final Integer agentCode, @Header("bankCode") final String bankCode) {
        if (agentCode != null) {
            List<PeocitUserProjection> peousers = peocitUserRepo.findUsersByAgentCode_bankCode(agentCode, bankCode);
            return peousers;
        } else
            throw new RuntimeException("Agent not found");

    }

    public List<SledgerUserProjection> fetchCustomersSledger(@Header("agentCode") final Integer agentCode, @Header("bankCode") final String bankCode) {
        if (agentCode != null) {
            List<SledgerUserProjection> susers = sledgerUserRepo.findUsersByAgentCode_bankCode(agentCode, bankCode);
            return susers;

        } else {
            throw new RuntimeException("Agent not found");
        }
    }
}

