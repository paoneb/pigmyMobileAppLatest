package com.pigmyMobileApp.repository;

import com.pigmyMobileApp.model.SchemeMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SchemeMappingRepo extends JpaRepository<SchemeMapping, String> {

    @Query("SELECT s.schemeID FROM SchemeMapping s WHERE s.schemeName = :schemename AND s.bankCode = :bankcode")
    String findBySchemeId( String schemename,String bankcode);
}
