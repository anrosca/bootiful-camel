package com.endava.camel.messaging;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class DirectDemoRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:csv_request")
                .process(exchange -> {
                    TimeUnit.SECONDS.sleep(2);
                })
                .log("Processing csv request: ${body}");
    }
}
