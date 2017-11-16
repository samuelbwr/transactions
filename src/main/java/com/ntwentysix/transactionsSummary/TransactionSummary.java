package com.ntwentysix.transactionsSummary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Setter
@Getter
@ToString
public class TransactionSummary {
    private BigDecimal sum;
    private BigDecimal avg;
    @JsonIgnore
    private List<BigDecimal> amounts;
    private AtomicInteger count;

    public TransactionSummary() {
    }

    public static TransactionSummary getDefaultInstance() {
        TransactionSummary transactionSummary = new TransactionSummary();
        transactionSummary.setSum( BigDecimal.ZERO );
        transactionSummary.setAvg( BigDecimal.ZERO );
        transactionSummary.setAmounts( new ArrayList<>() );
        transactionSummary.setCount( new AtomicInteger() );
        return transactionSummary;
    }

    @JsonProperty
    public BigDecimal getMax() {
        return amounts.get( amounts.size() - 1 );
    }

    @JsonProperty
    public BigDecimal getMin() {
        return amounts.get( 0 );
    }

    public synchronized void update(BigDecimal amount) {
        this.sum = this.sum.add( amount );
        this.getAmounts().add( amount );
        Collections.sort( this.getAmounts() );
        this.avg = this.sum.divide( new BigDecimal( count.incrementAndGet() ), BigDecimal.ROUND_HALF_DOWN );
    }
}
