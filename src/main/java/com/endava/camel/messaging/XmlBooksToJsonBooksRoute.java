package com.endava.camel.messaging;

import com.endava.camel.messaging.util.BookXmlSplitter;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class XmlBooksToJsonBooksRoute extends RouteBuilder {
    private final BookXmlSplitter splitter;

    @Override
    public void configure() throws Exception {
        from("direct:process_xml_books")
                .transform(bodyAs(String.class))
                .split(method(splitter))
                .to("direct:books_to_json");
    }
}
