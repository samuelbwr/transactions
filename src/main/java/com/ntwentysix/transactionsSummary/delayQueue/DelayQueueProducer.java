package com.ntwentysix.transactionsSummary.delayQueue;

import com.ntwentysix.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class DelayQueueProducer {

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private DelayQueueStateHolder delayQueueStateHolder;

    @Autowired
    private DelayQueueConsumer delayQueueConsumer;

    public void addTransactionToDelayQueue(Transaction transaction) {
        boolean shouldStartThread = delayQueueStateHolder.getQueue().isEmpty();
        putTransactionOnQueue( transaction );
        startThreadIfNecessary( shouldStartThread );
    }

    private void startThreadIfNecessary(boolean shouldStartThread) {
        if (shouldStartThread)
            taskExecutor.execute( delayQueueConsumer );
    }

    private void putTransactionOnQueue(Transaction transaction) {
        try {
            delayQueueStateHolder.getQueue().put( transaction );
        } catch (InterruptedException e) {
            throw new ThreadInterruptedException();
        }
    }
}
