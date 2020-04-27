package com.endava.camel.messaging;

import com.endava.camel.book.domain.Book;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class BookRecipientList {

    public void setRecipientListHeader(Exchange exchange) {
        List<String> recipients = listOf("file:output?fileName=book-${header.title}.json");
        Message in = exchange.getIn();
        Book book = in.getBody(Book.class);
        if (book.getTitle().contains("Spring")) {
            recipients.add("jms:spring_books");
        }
        in.setHeader("recipients", recipients.toArray(new String[0]));
    }

    private List<String> listOf(String ...values) {
        return new ArrayList<>(Arrays.asList(values));
    }
}
