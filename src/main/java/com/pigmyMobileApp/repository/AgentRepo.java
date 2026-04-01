package com.pigmyMobileApp.repository;

import com.pigmyMobileApp.model.AgentLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AgentRepo extends JpaRepository<AgentLogin,Long> {


    AgentLogin findByPhone(String phone);
}
