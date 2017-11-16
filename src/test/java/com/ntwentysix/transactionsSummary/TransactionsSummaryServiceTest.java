package com.ntwentysix.transactionsSummary;

import com.ntwentysix.configurations.ApplicationProperties;
import com.ntwentysix.transactions.Transaction;
import com.ntwentysix.transactionsSummary.delayQueue.DelayQueueProducer;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static com.ntwentysix.transactions.TransactionStub.DELAYED_TRANSACTION;
import static com.ntwentysix.transactions.TransactionStub.TEN_BUCKS_TRANSACTION;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionsSummaryServiceTest {

    @Autowired
    private TransactionsSummaryService service;

    @Autowired
    private ApplicationProperties applicationProperties;

    @MockBean
    private DelayQueueProducer delayQueueProducer;

    @MockBean
    private TransactionsSummaryUpdater transactionsSummaryUpdater;

    @Captor
    private ArgumentCaptor<Transaction> transactionArgumentCaptor;

    @Test
    public void ensureCanApplyTransactionToSummary() {
        long now = System.currentTimeMillis();
        TEN_BUCKS_TRANSACTION.setTimestamp( now );
        Boolean computed = service.computeTransactionIfInPeriod( TEN_BUCKS_TRANSACTION, now );
        verify( transactionsSummaryUpdater ).applyAmountToSummary( TEN_BUCKS_TRANSACTION.getAmount() );
        verify( delayQueueProducer ).addTransactionToDelayQueue( transactionArgumentCaptor.capture() );
        assertTrue( computed );
        assertThat( transactionArgumentCaptor.getValue().getDelay(), CoreMatchers.equalTo( applicationProperties.getStatisticsPeriodInMs() ) );
    }

    @Test
    public void ensureCantApplyTransactionToSummary() {
        Boolean computed = service.computeTransactionIfInPeriod( DELAYED_TRANSACTION, System.currentTimeMillis() );
        verify( transactionsSummaryUpdater, times( 0 ) ).applyAmountToSummary( DELAYED_TRANSACTION.getAmount() );
        verify( delayQueueProducer, times( 0 ) ).addTransactionToDelayQueue( DELAYED_TRANSACTION );
        assertFalse( computed );
    }
}
