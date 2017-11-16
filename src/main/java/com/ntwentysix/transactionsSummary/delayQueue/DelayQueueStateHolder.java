package com.ntwentysix.transactionsSummary.delayQueue;

import com.ntwentysix.transactions.Transaction;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

@Component
@Getter
public class DelayQueueStateHolder {
    private BlockingQueue<Transaction> queue;

    @PostConstruct
    public void postConstruct() {
        queue = new DelayQueue<>();
    }
}
