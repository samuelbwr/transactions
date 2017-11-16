package com.ntwentysix.transactionsSummary;

import com.ntwentysix.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Component
public class TransactionsSummaryService {

    private static TransactionSummary instance;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private DelayQueueConsumer queueConsumer;

    @Autowired
    private DelayQueueHolder queueHolder;

    @PostConstruct
    public void postConstruct(){
        instance = TransactionSummary.getDefaultInstance();
    }

    public void addTransaction(Transaction transaction) throws InterruptedException {
        boolean shouldStartThread = queueHolder.getQueue().isEmpty();
        queueHolder.getQueue().put( transaction );
        if (shouldStartThread)
            taskExecutor.execute( queueConsumer );
    }

    public TransactionSummary getActiveInstance() {
        return instance;
    }
}
