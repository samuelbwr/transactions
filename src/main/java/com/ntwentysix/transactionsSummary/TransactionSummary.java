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


@Setter
@Getter
@ToString
public class TransactionSummary {
    private BigDecimal sum;
    @JsonIgnore
    private List<BigDecimal> amounts;

    private TransactionSummary() {
    }

    public static TransactionSummary getDefaultInstance() {
        TransactionSummary transactionSummary = new TransactionSummary();
        transactionSummary.setSum( BigDecimal.ZERO );
        transactionSummary.setAmounts( new ArrayList<>() );
        return transactionSummary;
    }

    public synchronized void removeAmount(BigDecimal amount) {
        this.sum = this.sum.subtract( amount );
        amounts.remove( amount );
        Collections.sort( this.amounts );
    }

    public synchronized void addAmount(BigDecimal amount) {
        this.sum = this.sum.add( amount );
        this.amounts.add( amount );
        Collections.sort( this.amounts );
    }

    @JsonProperty
    public BigDecimal getMax() {
        return amounts.get( amounts.size() - 1 );
    }

    @JsonProperty
    public BigDecimal getMin() {
        return amounts.get( 0 );
    }

    @JsonProperty
    public BigDecimal getAvg() {
        return this.sum.divide( BigDecimal.valueOf( this.amounts.size() ), BigDecimal.ROUND_HALF_DOWN );
    }

    @JsonProperty
    public int getCount() {
        return this.amounts.size();
    }
}
