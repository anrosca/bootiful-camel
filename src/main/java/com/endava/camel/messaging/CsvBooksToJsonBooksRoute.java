package com.endava.camel.messaging;

import com.endava.camel.book.domain.Book;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CsvBooksToJsonBooksRoute extends RouteBuilder {
    private final BooksSplitter booksSplitter;
    @Override
    public void configure() throws Exception {
        from("direct:books")
                .log("Moving file: ${body}")
                .filter(this::isXmlOrCsv)
                .choice()
                    .when(header("FileName").endsWith("xml"))
                    .to("direct:process_xml_books").endChoice()
                .otherwise()
                .split(method(booksSplitter))
                .transform().body(Book::from)
                .to("direct:books_to_json");
    }

    private boolean isXmlOrCsv(Exchange exchange) {
        Message in = exchange.getIn();
        String fileName = in.getHeader("FileName", String.class);
        return fileName.endsWith("xml") || fileName.endsWith("csv");
    }
}
