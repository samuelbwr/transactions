package com.ntwentysix.transactionsSummary;

import com.ntwentysix.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DelayQueueConsumer implements Runnable {

    @Autowired
    private DelayQueueContext delayQueueContext;

    @Autowired
    private TransactionsSummaryService transactionsSummaryService;

    @Override
    public void run() {
        while (!delayQueueContext.getQueue().isEmpty())
            try {
                Transaction object = delayQueueContext.getQueue().take();
                transactionsSummaryService.getActiveInstance().removeAmount( object.getAmount() );

                System.out.println( "Consumer take: " + object + " at: " + System.currentTimeMillis() );
                System.out.println( "Summary is: " + transactionsSummaryService.getActiveInstance() );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
