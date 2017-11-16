package com.ntwentysix.transactionsSummary;

import com.ntwentysix.configurations.ApplicationProperties;
import com.ntwentysix.transactions.Transaction;
import com.ntwentysix.transactionsSummary.delayQueue.DelayQueueProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionsSummaryService {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private DelayQueueProducer delayQueueProducer;

    @Autowired
    private TransactionsSummaryUpdater transactionsSummaryUpdater;

    public boolean computeTransactionIfInPeriod(Transaction transaction, Long now) {
        if (!isTransactionInPeriod( transaction, now ))
            return false;
        setTimeInDelayQueueForTransaction( transaction, now );
        transactionsSummaryUpdater.applyAmountToSummary( transaction.getAmount() );
        delayQueueProducer.addTransactionToDelayQueue( transaction );
        return true;
    }

    private void setTimeInDelayQueueForTransaction(Transaction transaction, long now) {
        transaction.setDelay( calculateTransactionTimeInDelayQueue( transaction, now ) );
    }

    private long calculateTransactionTimeInDelayQueue(Transaction transaction, long now) {
        return (transaction.getTimestamp() - now) + applicationProperties.getStatisticsPeriodInMs();
    }

    private boolean isTransactionInPeriod(Transaction transaction, long now) {
        return (now - transaction.getTimestamp()) < applicationProperties.getStatisticsPeriodInMs();
    }
}
