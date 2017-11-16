package com.ntwentysix.transactions;

import jersey.repackaged.com.google.common.primitives.Ints;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;


@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor
public class Transaction implements Delayed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal amount;
    private Long timestamp;

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = timestamp - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Ints.saturatedCast(this.timestamp - ((Transaction) o).timestamp);
    }
}
