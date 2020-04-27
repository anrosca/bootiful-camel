package com.endava.camel.messaging;

import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ErrorHandlingDemo extends RouteBuilder {
    @Override
    public void configure() throws Exception {
//        errorHandler(deadLetterChannel("jms:dead_letters"));
        errorHandler(defaultErrorHandler().maximumRedeliveries(5).redeliveryDelay(1000));

        onException(NullPointerException.class)
                .process(exchange -> {
                    System.out.println("Got NPE");
                });


        from("jms:request.q")
                .doTry()
                    .process(exchange -> {
                        Message in = exchange.getIn();
                        String body = in.getBody(String.class);
                        System.out.println("Pressing message:" + body);
                        if (true) {
                            throw new NullPointerException();
                        }
                    })
                .doCatch(NullPointerException.class)
                    .to("jms:null_pointers");
    }
}
