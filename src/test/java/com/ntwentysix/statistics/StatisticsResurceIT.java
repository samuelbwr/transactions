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
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static com.ntwentysix.transactions.TransactionStub.TEN_BUCKS_TRANSACTION;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = App.class)
public class StatisticsResurceIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TransactionsSummaryStateHolder transactionsSummaryStateHolder;

    @Before
    public void reset() {
        transactionsSummaryStateHolder.resetInstance();
    }

    @Test
    public void ensureCanRetriveStatistics() throws Exception {
        IntStream.range( 1, 200 ).forEach( value -> usage().accept( TEN_BUCKS_TRANSACTION ) );
        StatisticsDTO entity = restTemplate.getForObject( "/statistics", StatisticsDTO.class );
        assertThat( entity.getSum(), equalTo( BigDecimal.valueOf( 1990 ) ) );
        assertThat( entity.getAvg(), equalTo( BigDecimal.TEN ) );
        assertThat( entity.getMax(), equalTo( BigDecimal.TEN ) );
        assertThat( entity.getMin(), equalTo( BigDecimal.TEN ) );
        assertThat( entity.getCount(), equalTo( 199 ) );
    }

    private Consumer<Transaction> usage() {
        return transaction -> restTemplate.postForEntity( "/transactions", transaction, String.class );
    }
}
