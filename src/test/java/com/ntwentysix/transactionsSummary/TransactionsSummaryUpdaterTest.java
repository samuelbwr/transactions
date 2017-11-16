package com.ntwentysix.transactionsSummary;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionsSummaryUpdaterTest {

    @Autowired
    private TransactionsSummaryUpdater updater;

    @Autowired
    private TransactionsSummaryStateHolder transactionsSummaryStateHolder;

    @Before
    public void reset(){
        transactionsSummaryStateHolder.resetInstance();
    }

    @Test
    public void ensureCanApplyAmountToSummary() {
        updater.applyAmountToSummary( BigDecimal.TEN );
        assertTransactionSummary();
    }

    @Test
    public void ensureCanRemoveAmountToSummary() {
        updater.applyAmountToSummary( BigDecimal.TEN );
        updater.applyAmountToSummary( BigDecimal.TEN );
        updater.removeAmountFromSummary( BigDecimal.TEN );
        assertTransactionSummary();
    }

    private void assertTransactionSummary() {
        assertThat( transactionsSummaryStateHolder.getInstance().getAmounts().size(), equalTo( 1 ) );
        assertThat( transactionsSummaryStateHolder.getInstance().getAmounts().get( 0 ), equalTo( BigDecimal.TEN ) );
        assertThat( transactionsSummaryStateHolder.getInstance().getSum(), equalTo( BigDecimal.TEN ) );
    }

}
