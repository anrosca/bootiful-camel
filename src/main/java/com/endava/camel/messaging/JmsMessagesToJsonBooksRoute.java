package com.endava.camel.messaging;

import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JmsMessagesToJsonBooksRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("jms:request.q")
                .to("direct:books");
    }
}
