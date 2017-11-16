package com.ntwentysix.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/transactions")
public class TransactionResource {

    @Autowired
    private TransactionService transactionService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertTransaction(Transaction transaction) throws InterruptedException {
        transactionService.insertTransaction( transaction );
        if (transaction.getTimestamp() < (System.currentTimeMillis() - 60000))
            return Response.status( Response.Status.NO_CONTENT ).build();
        return Response.status( Response.Status.CREATED ).build();
    }
}
