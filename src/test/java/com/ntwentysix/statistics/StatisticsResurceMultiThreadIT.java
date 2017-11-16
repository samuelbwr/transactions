package com.ntwentysix.statistics;

import com.ntwentysix.App;
import com.ntwentysix.transactions.Transaction;
import com.ntwentysix.transactionsSummary.TransactionsSummaryStateHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import static com.ntwentysix.transactions.TransactionStub.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = App.class)
public class StatisticsResurceMultiThreadIT {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TransactionsSummaryStateHolder transactionsSummaryStateHolder;

    @Before
    public void reset() {
        transactionsSummaryStateHolder.resetInstance();
    }

    @Test
    public void testMultiThreading() throws Exception {
        int ten_bucks = 5;
        int hundred_bucks = 3;
        int two_bucks = 6;
        int delayed = 9;
        int validIteractionSum = ten_bucks + hundred_bucks + two_bucks;

        final CountDownLatch startSignal = new CountDownLatch( 1 );
        final CountDownLatch doneSignal = new CountDownLatch( ten_bucks + hundred_bucks + two_bucks + delayed );
        prepareThread( ten_bucks, startSignal, doneSignal, TEN_BUCKS_TRANSACTION );
        prepareThread( two_bucks, startSignal, doneSignal, TWO_BUCKS_TRANSACTION );
        prepareThread( hundred_bucks, startSignal, doneSignal, HUNDRED_BUCKS_TRANSACTION );
        prepareThread( delayed, startSignal, doneSignal, DELAYED_TRANSACTION );
        startSignal.countDown();
        doneSignal.await();

        StatisticsDTO entity = restTemplate.getForObject( "/statistics", StatisticsDTO.class );
        assertThat( entity.getSum(), equalTo( BigDecimal.valueOf( 362 ) ) );
        assertThat( entity.getAvg(), equalTo( BigDecimal.valueOf( 26 ) ) );
        assertThat( entity.getMax(), equalTo( BigDecimal.valueOf( 100 ) ) );
        assertThat( entity.getMin(), equalTo( BigDecimal.valueOf( 2 ) ) );
        assertThat( entity.getCount(), equalTo( validIteractionSum ) );
    }

    private void prepareThread(int iterations, final CountDownLatch startSignal, final CountDownLatch doneSignal, final Transaction transaction) {
        for (int i = 0; i < iterations; i++) {
            Thread thread = new Thread() {
                public void run() {
                    try {
                        startSignal.await();
                        usage().accept( transaction );
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        doneSignal.countDown();
                    }
                }
            };
            thread.start();
        }
    }

    private Consumer<Transaction> usage() {
        return transaction -> restTemplate.postForEntity( "/transactions", transaction, String.class );
    }
}
