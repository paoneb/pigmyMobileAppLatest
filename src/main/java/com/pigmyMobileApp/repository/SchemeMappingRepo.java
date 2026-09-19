package com.pigmyMobileApp.repository;

import com.pigmyMobileApp.model.SchemeMapping;
import com.pigmyMobileApp.model.peocit.SchemesDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SchemeMappingRepo extends JpaRepository<SchemeMapping, String> {

    @Query("SELECT new com.pigmyMobileApp.model.peocit.SchemesDTO(s.schemeID, s.schemeName)  FROM SchemeMapping s WHERE s.bankCode = :bankcode")
    List<SchemesDTO> findByBankCode(String bankcode);
}
