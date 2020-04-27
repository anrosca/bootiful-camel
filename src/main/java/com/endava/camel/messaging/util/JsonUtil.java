package com.endava.camel.messaging.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class JsonUtil {

    @SneakyThrows
    public Object toJson(Object object) {
        return new ObjectMapper().writeValueAsString(object);
    }
}
