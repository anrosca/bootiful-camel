package com.endava.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CamelFiddleApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamelFiddleApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(CamelContext context) {
        return args -> {
            ProducerTemplate producerTemplate = context.createProducerTemplate();
//            sendXmlBook(producerTemplate);
//            for (int i = 0; i < 5; ++i) {
//                producerTemplate.sendBody("direct:csv_request", "Hello, world!");
//            }
        };
    }

    private void sendXmlBook(ProducerTemplate producerTemplate) {
        producerTemplate.sendBodyAndHeader("jms:request.q",
                """
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
            <book>
                <title>Docker in Practice, Second Edition</title>
                <publishYear>2019</publishYear>
                <authors>
                    <author>Ian Miell</author>
                    <author>Aidan Hobson Sayers</author>
                </authors>
                <isbn>9781617294808</isbn>
            </book>
        </books>
                        """,
                "FileName", "books.xml");
    }
}
