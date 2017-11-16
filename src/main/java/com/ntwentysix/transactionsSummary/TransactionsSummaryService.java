package com.ntwentysix.transactionsSummary;

import com.ntwentysix.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TransactionsSummaryService {

    private static TransactionSummary instance;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private DelayQueueConsumer queueConsumer;

    @Autowired
    private DelayQueueContext queueContext;

    @PostConstruct
    public void postConstruct(){
        instance = TransactionSummary.getDefaultInstance();
    }

    public void addTransaction(Transaction transaction) throws InterruptedException {
        if(transaction.shouldBeAddedToQueue()) {
            instance.addAmount( transaction.getAmount() );
            System.out.println( "Summary this: " + getActiveInstance() );
            addToQueue( transaction );
        }
    }

    private void addToQueue(Transaction transaction) throws InterruptedException {
        boolean shouldStartThread = queueContext.getQueue().isEmpty();
        queueContext.getQueue().put( transaction );
        if (shouldStartThread)
            taskExecutor.execute( queueConsumer );
    }

    public TransactionSummary getActiveInstance() {
        return instance;
    }
}
