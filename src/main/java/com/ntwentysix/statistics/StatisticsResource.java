package com.ntwentysix.statistics;

import com.ntwentysix.transactionsSummary.TransactionsSummaryStateHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsResource {

    @Autowired
    private TransactionsSummaryStateHolder transactionsSummaryStateHolder;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public StatisticsDTO getStatistics() {
        return transactionsSummaryStateHolder.getInstance().asStatistic();
    }
}
