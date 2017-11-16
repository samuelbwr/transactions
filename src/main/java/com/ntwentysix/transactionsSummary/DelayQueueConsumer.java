package com.ntwentysix.transactionsSummary;

import com.ntwentysix.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DelayQueueConsumer implements Runnable {

    @Autowired
    private DelayQueueHolder delayQueueHolder;

    @Autowired
    private TransactionsSummaryService transactionsSummaryService;

    @Override
    public void run() {
        while (!delayQueueHolder.getQueue().isEmpty())
            try {
                Transaction object = delayQueueHolder.getQueue().take();
                transactionsSummaryService.getActiveInstance().update( object.getAmount() );

                System.out.println( "Consumer take: " + object + " at: " + System.currentTimeMillis() );
                System.out.println( "Summary is: " + transactionsSummaryService.getActiveInstance() );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
