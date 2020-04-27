package com.endava.camel.messaging;

import com.endava.camel.book.domain.Author;
import com.endava.camel.book.domain.Book;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

@SpringBootTest
@Testcontainers
@ExtendWith(SpringExtension.class)
public class JmsCsvAndXmlFileToJsonRouteIT {

    private static final String XML_MESSAGE = """
            <?xml version="1.0" encoding="utf-9" ?>
            <books>
                <book>
                    <title>Spring in Action, Fifth Edition</title>
                    <publishYear>2018</publishYear>
                    <authors>
                        <author>Craig Walls</author>
                    </authors>
                    <isbn>9781617294945</isbn>
                </book>
                <book>
                    <title>Camel in Action, Second Edition</title>
                    <publishYear>2018</publishYear>
                    <authors>
                        <author>Claus Ibsen</author>
                        <author>Jonathan Anstey</author>
                    </authors>
                    <isbn>9781617292934</isbn>
                </book>
            </books>
                """;
    private static final String XML_FILE_NAME = "books.xml";
    private static final String FILE_NAME_HEADER = "FileName";

    @Container
    public static GenericContainer<?> activeMqContainer = new GenericContainer<>("rmohr/activemq:latest")
            .withExposedPorts(61616);

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>();

    @Autowired
    private CamelContext camelContext;

    @DynamicPropertySource
    static void activemqProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.activemq.broker-url", () -> "tcp://localhost:" + activeMqContainer.getMappedPort(61616));
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    public void test() throws JsonProcessingException {
        ProducerTemplate producerTemplate = camelContext.createProducerTemplate();
        producerTemplate.sendBodyAndHeader("jms:request.q", XML_MESSAGE, FILE_NAME_HEADER, XML_FILE_NAME);

        ConsumerTemplate consumerTemplate = camelContext.createConsumerTemplate();
        Book firstBook = fromJson(consumerTemplate.receiveBody("jms:books_audit.q", String.class));
        Book secondBook = fromJson(consumerTemplate.receiveBody("jms:books_audit.q", String.class));

        assertEquals(Book.builder()
                .title("Spring in Action, Fifth Edition")
                .publishYear(2018)
                .authors(List.of(new Author("Craig Walls")))
                .isbn("9781617294945")
                .build(), firstBook);
        assertEquals(Book.builder()
                .title("Camel in Action, Second Edition")
                .publishYear(2018)
                .authors(List.of(new Author("Claus Ibsen"), new Author("Jonathan Anstey")))
                .isbn("9781617292934")
                .build(), secondBook);
    }

    private static Book fromJson(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, Book.class);
    }
}
