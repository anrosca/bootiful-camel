package com.endava.camel.messaging;

import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class BooksSplitter {

    @Handler
    public List<String> splitByNewLine(String message) {
        return Arrays.asList(message.split("\r\n"));
    }

    public List<String> splitByComma(String message) {
        return Arrays.asList(message.split(","));
    }
}
