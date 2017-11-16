package com.ntwentysix.transactionsSummary;

import com.ntwentysix.statistics.StatisticsDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@ToString
@Accessors(chain = true)
public class TransactionSummary {
    private BigDecimal sum;
    private List<BigDecimal> amounts;

    private TransactionSummary() {
    }

    public static TransactionSummary getDefaultInstance() {
        TransactionSummary transactionSummary = new TransactionSummary();
        transactionSummary.setSum( BigDecimal.ZERO );
        transactionSummary.setAmounts( new ArrayList<>() );
        return transactionSummary;
    }

    public BigDecimal getMax() {
        if(amounts.size() > 0)
            return amounts.get( amounts.size() - 1 );
        return BigDecimal.ZERO;
    }

    public BigDecimal getMin() {
        if(amounts.size() > 0)
            return amounts.get( 0 );
        return BigDecimal.ZERO;
    }

    public BigDecimal getAvg() {
        if (amounts.size() > 0)
            return this.sum.divide( BigDecimal.valueOf( this.amounts.size() ), BigDecimal.ROUND_HALF_DOWN );
        return BigDecimal.ZERO;
    }

    public int getCount() {
        return this.amounts.size();
    }

    public StatisticsDTO asStatistic() {
        return new StatisticsDTO( sum, getAvg(), getMax(), getMin(), getCount() );
    }
}
