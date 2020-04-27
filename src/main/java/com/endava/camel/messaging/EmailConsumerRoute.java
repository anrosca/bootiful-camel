package com.endava.camel.messaging;

import com.endava.camel.config.properties.EmailProperties;
import com.endava.camel.messaging.processors.EmailAttachmentProcessor;
import com.endava.camel.messaging.util.BookXmlSplitter;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailConsumerRoute extends RouteBuilder {
    private final EmailProperties emailProperties;
    private final EmailAttachmentProcessor attachmentProcessor;
    private final BookXmlSplitter bookXmlSplitter;

    @Override
    public void configure() throws Exception {
        from(makeGmailRouteUri())
                .process(attachmentProcessor)
                .process(exchange -> {
                    String body = exchange.getIn().getBody(String.class);
                    System.out.println("Received email with attachment: " + body);
                })
                .transform(bodyAs(String.class))
                .split().body(bookXmlSplitter::split)
                .to("direct:books_to_json");
    }

    private String makeGmailRouteUri() {
        return String.format("imaps://imap.gmail.com?username=%s&password=%s&delete=%s&unseen=%s&delay=%s",
                emailProperties.getEmailAddress(), emailProperties.getPassword(), emailProperties.getDeleteEmails(),
                emailProperties.getUnseenOnly(), emailProperties.getDelay());
    }
}
