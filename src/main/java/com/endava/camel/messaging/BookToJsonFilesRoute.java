package com.endava.camel.messaging;

import com.endava.camel.book.domain.Book;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookToJsonFilesRoute extends RouteBuilder {
    private final JsonDataFormat jsonDataFormat;
    private final BookRecipientList bookRecipientList;

    @Override
    public void configure() throws Exception {
        from("direct:books_to_json")
                .process(this::setTitleHeader)
                .wireTap("direct:persist_book")
                .process(bookRecipientList::setRecipientListHeader)
                .marshal(jsonDataFormat)
                .transform(bodyAs(String.class))
                .process(exchange -> {
                    Message in = exchange.getIn();
                    Object body = in.getBody();
                    System.out.println("Pressing message: " + body);
                })
                .wireTap("jms:books_audit.q")
                .recipientList(header("recipients"));
    }

    private void setTitleHeader(Exchange exchange) {
        Message in = exchange.getIn();
        Book book = in.getBody(Book.class);
        in.setHeader("title", book.getTitle());
    }
}
