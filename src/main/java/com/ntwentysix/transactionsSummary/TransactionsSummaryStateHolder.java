package com.ntwentysix.transactionsSummary;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Getter
public class TransactionsSummaryStateHolder {
    private TransactionSummary instance;

    @PostConstruct
    public void postConstruct(){
        instance = TransactionSummary.getDefaultInstance();
    }

    public void resetInstance(){
        postConstruct();
    }
}
