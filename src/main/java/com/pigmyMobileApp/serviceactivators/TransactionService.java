package com.pigmyMobileApp.serviceactivators;


import com.pigmyMobileApp.model.FetchTransactionResponse;
import com.pigmyMobileApp.model.Transaction;
import com.pigmyMobileApp.model.TransactionRequest;
import com.pigmyMobileApp.model.TransactionSummaryProjection;
import com.pigmyMobileApp.model.peocit.PeocitTransaction;
import com.pigmyMobileApp.model.peocit.PeocitTransactionRequest;
import com.pigmyMobileApp.model.sledger.SledgerTransaction;
import com.pigmyMobileApp.model.sledger.SledgerTransactionRequest;
import com.pigmyMobileApp.repository.PeocitTransactionRepo;
import com.pigmyMobileApp.repository.SledgerTransactionRepo;
import com.pigmyMobileApp.repository.TransactionRepo;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Header;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("transactionService")
@RequiredArgsConstructor
public class TransactionService {


    private final TransactionRepo transactionRepo;
    private final PeocitTransactionRepo peocitTransactionRepo;
    private final SledgerTransactionRepo sledgerTransactionRepo;

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> addTransaction(@Body final TransactionRequest transactionRequest) {

        try {
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
            transactionToDB.setSchemeId(transactionRequest.getSchemeId());
            transactionToDB.setSchemename(transactionRequest.getSchemename());
            transactionToDB.setUserId(transactionRequest.getUserId());
            transactionToDB.setTransactionId(transactionRequest.getTransactionId());

            transactionRepo.save(transactionToDB);
            return ResponseEntity.ok(
                    Map.of(
                            "status", "success",
                            "message", "BankSoft Transaction done successfully"
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of(
                            "status", "error",
                            "message", "BankSoft Transaction failed: " + e.getMessage()
                    )
            );
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity addTransactionPeocit(@Body final PeocitTransactionRequest transactionRequestPeocit) {
        try {
            PeocitTransaction transactionPeocitToDB = new PeocitTransaction();

            transactionPeocitToDB.setBankCode(transactionRequestPeocit.getBankCode());
            transactionPeocitToDB.setAgentCode(transactionRequestPeocit.getAgentCode());
            transactionPeocitToDB.setAgentname(transactionRequestPeocit.getAgentName());
            transactionPeocitToDB.setCustomerName(transactionRequestPeocit.getCustomerName());
            transactionPeocitToDB.setAccountNumber(transactionRequestPeocit.getAccountNumber());
            transactionPeocitToDB.setCollectedAmount(transactionRequestPeocit.getCollectedAmount());
            transactionPeocitToDB.setFinalAmount(transactionRequestPeocit.getFinalAmount());
            transactionPeocitToDB.setCollectedDate(LocalDate.now());
            transactionPeocitToDB.setCollectiontype(transactionRequestPeocit.getCollectiontype());
            transactionPeocitToDB.setStatus("Collected");
            transactionPeocitToDB.setSchemeId(transactionRequestPeocit.getSchemeId());
            transactionPeocitToDB.setSchemename(transactionRequestPeocit.getSchemename());
            transactionPeocitToDB.setUserId(transactionRequestPeocit.getUserId());
            transactionPeocitToDB.setTransactionId(transactionRequestPeocit.getTransactionId());
            transactionPeocitToDB.setVpncode(transactionRequestPeocit.getVpncode());

            peocitTransactionRepo.save(transactionPeocitToDB);
            return ResponseEntity.ok(
                    Map.of(
                            "status", "success",
                            "message", "Peocit Transaction done successfully"
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of(
                            "status", "error",
                            "message", "Peocit Transaction failed: " + e.getMessage()
                    )
            );
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity addTransactionSledger(@Body final SledgerTransactionRequest transactionRequest) {
        try {
            SledgerTransaction sledgertransactionToDB = new SledgerTransaction();

            sledgertransactionToDB.setBankCode(transactionRequest.getBankCode());
            sledgertransactionToDB.setAgentCode(transactionRequest.getAgentCode());
            sledgertransactionToDB.setAgentname(transactionRequest.getAgentName());
            sledgertransactionToDB.setCustomerName(transactionRequest.getCustomerName());
            sledgertransactionToDB.setAccountNumber(transactionRequest.getAccountNumber());
            sledgertransactionToDB.setCollectedAmount(transactionRequest.getCollectedAmount());
            sledgertransactionToDB.setCollectedDate(LocalDate.now());
            sledgertransactionToDB.setCollectiontype(transactionRequest.getCollectiontype());
            sledgertransactionToDB.setStatus("Collected");
            sledgertransactionToDB.setSchemeId(transactionRequest.getSchemeId());
            sledgertransactionToDB.setUserId(transactionRequest.getUserId());
            sledgertransactionToDB.setTransactionId(transactionRequest.getTransactionId());
            sledgertransactionToDB.setBranchCode(transactionRequest.getBranchCode());

            sledgerTransactionRepo.save(sledgertransactionToDB);
            return ResponseEntity.ok(
                    Map.of(
                            "status", "success",
                            "message", "Sledger Transaction done successfully"
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of(
                            "status", "error",
                            "message", "Sledger Transaction failed: " + e.getMessage()
                    )
            );
        }
    }

    public List<FetchTransactionResponse> fetchTransaction(@Header("agentCode") final Integer agCode, @Header("bankCode") final String bankCode, @Header("date") final LocalDate selectedDate, final Exchange e) {
        List<Transaction> tr = transactionRepo.findByAgentCodeAndBankCodeAndCollectedDateAndstatus(agCode, bankCode, selectedDate);
        List<FetchTransactionResponse> rs = new ArrayList<>();

        for (Transaction k : tr) {
            FetchTransactionResponse response = FetchTransactionResponse.builder()
                    .trasactionId(k.getId())
                    .collectedAmount((double) k.getCollectedAmount())
                    .customerName(k.getCustomerName())
                    .accountNumber(k.getAccountNumber()).build();
            rs.add(response);
        }

        return rs;
    }

    public void fetchCollection(@Header("agentCode") final Integer agCode, @Header("bankCode") final String bankCode, @Header("bankType") final String bankType, final Exchange e) {

        if ("banksoft".equalsIgnoreCase(bankType)) {
            TransactionSummaryProjection transactionSummary = transactionRepo.findTransactionSummary(agCode, bankCode);

            e.getIn().setBody(Map.of(
                    "totalAmountCollected", transactionSummary.getTotalCollected(),
                    "totalTransactions", transactionSummary.getTransactionCount()));

        } else if ("peocit".equalsIgnoreCase(bankType)) {
            TransactionSummaryProjection transactionSummary = peocitTransactionRepo.findTransactionSummary(agCode, bankCode);

            e.getIn().setBody(Map.of(
                    "totalAmountCollected", transactionSummary.getTotalCollected(),
                    "totalTransactions", transactionSummary.getTransactionCount()));
        }
        else if ("sledger".equalsIgnoreCase(bankType)) {
            TransactionSummaryProjection transactionSummary = sledgerTransactionRepo.findTransactionSummary(agCode, bankCode);

            e.getIn().setBody(Map.of(
                    "totalAmountCollected", transactionSummary.getTotalCollected(),
                    "totalTransactions", transactionSummary.getTransactionCount()));
        }
    }
}
