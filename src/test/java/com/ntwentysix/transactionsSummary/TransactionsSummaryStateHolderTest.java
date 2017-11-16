package com.ntwentysix.transactionsSummary;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionsSummaryStateHolderTest {

    @Autowired
    private TransactionsSummaryStateHolder holder;

    @Test
    public void ensureHasInstanceWhenIsCreated() {
        assertNotNull( holder.getInstance() );
    }

    @Test
    public void ensureCanResetInstance() {
        TransactionSummary summary = holder.getInstance();
        holder.resetInstance();
        assertNotEquals( summary, holder.getInstance() );
    }
}
