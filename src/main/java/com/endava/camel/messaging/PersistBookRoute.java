package com.endava.camel.messaging;

import com.endava.camel.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersistBookRoute extends RouteBuilder {
    private final BookService bookService;

    @Override
    public void configure() throws Exception {
        from("direct:persist_book")
                .bean(bookService);
    }
}
