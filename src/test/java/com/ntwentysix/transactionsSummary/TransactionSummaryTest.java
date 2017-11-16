package com.ntwentysix.transactionsSummary;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionSummaryTest {

    @Test
    public void ensureRetrieveCalculatedValues(){
        BigDecimal total = BigDecimal.valueOf( 12 );
        BigDecimal two = BigDecimal.valueOf( 2 );
        TransactionSummary transactionSummary = TransactionSummary.getDefaultInstance()
                .setAmounts( Arrays.asList( two, BigDecimal.TEN) ).setSum( total );
        assertThat( transactionSummary.getSum(), equalTo( total ) );
        assertThat( transactionSummary.getAvg(), equalTo( BigDecimal.valueOf( 6 ) ) );
        assertThat( transactionSummary.getCount(), equalTo( 2 ) );
    }

    @Test
    public void ensureRetrieveZeroWhenEmpty(){
        TransactionSummary transactionSummary = TransactionSummary.getDefaultInstance();
        assertThat( transactionSummary.getSum(), equalTo( BigDecimal.ZERO ) );
        assertThat( transactionSummary.getAvg(), equalTo( BigDecimal.ZERO ) );
        assertThat( transactionSummary.getMin(), equalTo( BigDecimal.ZERO ) );
        assertThat( transactionSummary.getMax(), equalTo( BigDecimal.ZERO ) );
        assertThat( transactionSummary.getCount(), equalTo( 0 ) );
    }
}
