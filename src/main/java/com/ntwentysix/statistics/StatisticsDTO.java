package com.ntwentysix.statistics;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@RequiredArgsConstructor
@Setter
@Getter
@ToString
public class StatisticsDTO implements Serializable{
    private final BigDecimal sum;
    private final BigDecimal avg;
    private final BigDecimal max;
    private final BigDecimal min;
    private final Integer count;

}
