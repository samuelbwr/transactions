package com.ntwentysix;

import com.ntwentysix.statistics.StatisticsResource;
import com.ntwentysix.transactions.TransactionResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    /**
     * In constructor we can define Jersey Resources &amp; Other Components
     */
    public JerseyConfig() {
        register( StatisticsResource.class );
        register( TransactionResource.class );
    }
}