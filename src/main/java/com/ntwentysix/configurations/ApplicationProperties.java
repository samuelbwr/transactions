package com.ntwentysix.configurations;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
@Getter
public class ApplicationProperties {

    @Value("${statistics-period-in-ms}")
    private long statisticsPeriodInMs;

}