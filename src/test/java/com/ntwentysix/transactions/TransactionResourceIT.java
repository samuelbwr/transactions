package com.ntwentysix.transactions;


import com.ntwentysix.App;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static com.ntwentysix.transactions.TransactionStub.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = App.class)
public class TransactionResourceIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void ensureCanInsertTransaction() throws Exception {
        ResponseEntity entity = restTemplate.postForEntity( "/transactions", TEN_BUCKS_TRANSACTION, String.class );
        assertThat( entity.getStatusCode(), equalTo( HttpStatus.CREATED ) );
    }

    @Test
    public void ensureCanInsertTransactionDelayed() throws Exception {
        ResponseEntity entity = restTemplate.postForEntity( "/transactions", DELAYED_TRANSACTION, String.class );
        assertThat( entity.getStatusCode(), equalTo( HttpStatus.NO_CONTENT ) );
    }


}
