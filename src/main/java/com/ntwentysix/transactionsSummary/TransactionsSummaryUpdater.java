package com.ntwentysix.transactionsSummary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;

@Component
public class TransactionsSummaryUpdater {

    @Autowired
    private TransactionsSummaryStateHolder transactionsSummaryStateHolder;

    public synchronized void removeAmountFromSummary(BigDecimal amount) {
        TransactionSummary transactionSummary = transactionsSummaryStateHolder.getInstance();
        transactionSummary.setSum( transactionSummary.getSum().subtract( amount ) );
        transactionSummary.getAmounts().remove( amount );
        sortAmounts( transactionSummary );
    }

    public synchronized void applyAmountToSummary(BigDecimal amount) {
        TransactionSummary transactionSummary = transactionsSummaryStateHolder.getInstance();
        transactionSummary.setSum( transactionSummary.getSum().add( amount ) );
        transactionSummary.getAmounts().add( amount );
        sortAmounts( transactionSummary );
    }

    private void sortAmounts(TransactionSummary transactionSummary) {
        Collections.sort( transactionSummary.getAmounts() );
    }

}
