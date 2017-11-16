package com.ntwentysix.transactionsSummary;

import java.math.BigDecimal;
import java.util.Arrays;

public interface TransactionsSummaryStub {

    TransactionSummary TWENTY_BUCKS_SUMMARY = TransactionSummary.getDefaultInstance().setAmounts( Arrays.asList( BigDecimal.valueOf( 20 ) ) )
                .setSum( BigDecimal.valueOf( 20 ) );
}
