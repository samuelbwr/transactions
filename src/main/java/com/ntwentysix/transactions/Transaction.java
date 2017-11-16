package com.ntwentysix.transactions;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;


@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Transaction implements Delayed {
    private static final int MINUTE = 60000;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal amount;
    private Long timestamp;

    @Transient
    private long delay;

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert( delay, TimeUnit.MILLISECONDS );
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.valueOf( this.timestamp - ((Transaction) o).timestamp ).intValue();
    }

}
