package com.ntwentysix.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionResource {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity insertTransaction(@RequestBody Transaction transaction) {
        if (transactionService.saveAndComputeToSummaryIfInPeriod( transaction ))
            return ResponseEntity.status( HttpStatus.CREATED ).build();
        return ResponseEntity.status( HttpStatus.NO_CONTENT ).build();
    }
}
