package com.ntwentysix.transactionsSummary.delayQueue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DelayQueueStateHolderTest {

    @Autowired
    private DelayQueueStateHolder holder;

    @Test
    public void ensureHasInstanceWhenIsCreated() {
        assertNotNull( holder.getQueue() );
    }

}
