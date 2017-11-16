package com.ntwentysix.transactionsSummary.delayQueue;

import com.ntwentysix.transactions.Transaction;
import com.ntwentysix.transactionsSummary.TransactionsSummaryUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DelayQueueConsumer implements Runnable {

    @Autowired
    private DelayQueueStateHolder delayQueueStateHolder;

    @Autowired
    private TransactionsSummaryUpdater transactionsSummaryUpdater;

    @Override
    public void run() {
        while (!delayQueueStateHolder.getQueue().isEmpty())
            try {
                Transaction transaction = delayQueueStateHolder.getQueue().take();
                transactionsSummaryUpdater.removeAmountFromSummary(transaction.getAmount() );
            } catch (InterruptedException e) {
                throw new ThreadInterruptedException();
            }
    }
}
