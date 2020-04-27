package com.endava.camel.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
//SEDA - Staged event driven architecture
public class SedaDemoRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("seda:csv_request?concurrentConsumers=5")
                .process(exchange -> {
                    TimeUnit.SECONDS.sleep(2);
                })
                .log("Processing csv request: ${body}");
    }
}
