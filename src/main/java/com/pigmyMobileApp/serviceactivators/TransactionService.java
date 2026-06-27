package com.pigmyMobileApp.serviceactivators;


import com.pigmyMobileApp.model.FetchTransactionResponse;
import com.pigmyMobileApp.model.Transaction;
import com.pigmyMobileApp.model.TransactionRequest;
import com.pigmyMobileApp.repository.SchemeMappingRepo;
import com.pigmyMobileApp.repository.TransactionRepo;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component("transactionService")
public class TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private SchemeMappingRepo schemeMappingRepo;

    public ResponseEntity<?> addTransaction(@Body  TransactionRequest transactionRequest, final Exchange e) {

        Transaction transactionToDB = new Transaction();

        transactionToDB.setBankCode(transactionRequest.getBankCode());
        transactionToDB.setAgentCode(transactionRequest.getAgentCode());
        transactionToDB.setAgentname(transactionRequest.getAgentName());
        transactionToDB.setCustomerName(transactionRequest.getCustomerName());
        transactionToDB.setAccountNumber(transactionRequest.getAccountNumber());
        transactionToDB.setCollectedAmount(transactionRequest.getCollectedAmount());
        transactionToDB.setCollectedDate(LocalDate.now());
        transactionToDB.setCollectiontype(transactionRequest.getCollectiontype());
        transactionToDB.setStatus("Collected");
        transactionToDB.setSchemeId(schemeMappingRepo.findBySchemeId(transactionRequest.getSchemename(),transactionRequest.getBankCode()));
        transactionToDB.setSchemename(transactionRequest.getSchemename());
        transactionToDB.setUserId(transactionRequest.getUserId());
        transactionToDB.setTransactionId(transactionRequest.getTransactionId());

         transactionRepo.save(transactionToDB);

        return ResponseEntity.ok(
                Map.of(
                        "status", "success",
                        "message", "Transaction done successfully"
                )
        );



    }

    public List<FetchTransactionResponse> fetchTransaction(@Header("agentCode") final Integer agCode, @Header("bankCode") final String bankCode, @Header("date") final LocalDate selectedDate, final Exchange e) {
        List<Transaction> tr = transactionRepo.findByAgentCodeAndBankCodeAndCollectedDateAndstatus(agCode, bankCode, selectedDate);
        List<FetchTransactionResponse> rs = new ArrayList<>();

        for (Transaction k : tr) {
            FetchTransactionResponse response = FetchTransactionResponse.builder()
                    .trasactionId(k.getId())
                    .collectedAmount(k.getCollectedAmount())
                    .customerName(k.getCustomerName())
                    .accountNumber(k.getAccountNumber()).build();
            rs.add(response);
        }

        return rs;
    }

    public ResponseEntity deleteTransaction(@Header("transactionId") final long id, final Exchange e) {
        if (!transactionRepo.existsById(id)) {
            throw new RuntimeException("Transaction not found with id: " + id);
        }
        transactionRepo.deleteById(id);
        return ResponseEntity.ok("Transaction deleted successfully");


    }
}
