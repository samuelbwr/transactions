package com.ntwentysix.transactions;

import com.ntwentysix.transactionsSummary.TransactionsSummaryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static com.ntwentysix.transactions.TransactionStub.TEN_BUCKS_TRANSACTION;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private TransactionsSummaryService transactionsSummaryService;


    @Test
    public void ensureCallsRepositoryAndSummaryServices() throws InterruptedException {
        Transaction transaction = TEN_BUCKS_TRANSACTION;
        transactionService.saveAndComputeToSummaryIfInPeriod( transaction );
        verify( transactionRepository ).save( transaction );
        verify( transactionsSummaryService ).computeTransactionIfInPeriod( any( Transaction.class ), any( Long.class ) );
    }

}
