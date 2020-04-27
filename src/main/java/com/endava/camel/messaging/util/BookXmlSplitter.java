package com.endava.camel.messaging.util;

import com.endava.camel.book.domain.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookXmlSplitter {

    @SneakyThrows
    public List<Book> split(Object body) {
        return new XmlMapper().readValue((String) body, new TypeReference<List<Book>>() {
        });
    }
}
