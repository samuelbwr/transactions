package com.ntwentysix.transactions;

import com.ntwentysix.transactionsSummary.TransactionsSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionsSummaryService transactionsSummaryService;

    public void insertTransaction(Transaction transaction) throws InterruptedException {
        transactionRepository.save( transaction );
        transactionsSummaryService.addTransaction( transaction );
    }
}
