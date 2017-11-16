package com.ntwentysix.statistics;

import com.ntwentysix.transactionsSummary.TransactionSummary;
import com.ntwentysix.transactionsSummary.TransactionsSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/statistics")
public class StatisticsResource {

    @Autowired
    private TransactionsSummaryService transactionsSummaryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TransactionSummary getStatistics() {
        return transactionsSummaryService.getActiveInstance();
    }
}
