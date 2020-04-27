package com.endava.camel.messaging;

import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CsvFilesToJsonBooksRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:input")
                .process(this::setFileNameHeader)
                .to("direct:books");
    }

    private void setFileNameHeader(Exchange exchange) {
        Message in = exchange.getIn();
        String camelFileName = in.getHeader("CamelFileName", String.class);
        in.setHeader("FileName", camelFileName);
    }
}
