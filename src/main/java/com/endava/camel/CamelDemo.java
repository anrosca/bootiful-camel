package com.endava.camel;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import java.util.List;
import java.util.Map;

public class CamelDemo {
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("file:input")
//                        .log("Moving file: ${body}")
                        .split(body().tokenize("\r\n"))
                        .transform().body(CamelDemo::toBook)
                        .transform().body(CamelDemo::toJson)
                        .to("file:output?fileName=book-${header.title}.json");
            }
        });
        context.start();
        System.out.println("Press any key to stop...");
        System.in.read();
        context.stop();
    }

    @SneakyThrows
    private static Object toJson(Object object, Map<String, Object> properties) {
        Book b = (Book) object;
        properties.put("title", b.getTitle());
        return new ObjectMapper().writeValueAsString(object);
    }

    private static Object toBook(Object line) {
        String[] parts = ((String)line).split(",");
        return Book.builder()
                .year(Integer.parseInt(parts[0]))
                .title(parts[1])
                .author(parts[2])
                .build();
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Book {
    private int year;
    private String title;
    private String author;
}

class BookProcessor {
    public void process(String parts) {
        System.out.println("!!!!: got parts: " + parts + ",!!!");
    }
}