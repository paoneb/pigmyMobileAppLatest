package com.pigmyMobileApp.serviceactivators;



import com.pigmyMobileApp.model.User;
import com.pigmyMobileApp.repository.UserRepo;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("addUserService")
public class AddUserService {

    @Autowired
    private UserRepo userRepo;


    public List<User> fetchCustomers(@Header("agentCode") final Integer agentCode, @Header("bankCode") final String bankCode)
    {
        if (agentCode != null) {
            List<User> users= userRepo.findUsersByAgentCode_bankCode(agentCode,bankCode);
            if (users.isEmpty()) {
                 throw new  RuntimeException("Agent not found");
            }
            return users;

        } else {
            return userRepo.findAll();
        }


    }
}
