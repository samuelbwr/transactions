package com.ntwentysix.transactions;

import java.math.BigDecimal;

public interface TransactionStub {

    Transaction TEN_BUCKS_TRANSACTION =
            new Transaction().setTimestamp( System.currentTimeMillis() ).setAmount( BigDecimal.TEN ),
            HUNDRED_BUCKS_TRANSACTION =
                    new Transaction().setTimestamp( System.currentTimeMillis() ).setAmount( new BigDecimal( 100 ) ),
            TWO_BUCKS_TRANSACTION =
                    new Transaction().setTimestamp( System.currentTimeMillis() ).setAmount( new BigDecimal( 2 ) ),
            DELAYED_TRANSACTION = new Transaction().setTimestamp( System.currentTimeMillis() - 10000000 ).setAmount( BigDecimal.TEN );
}
