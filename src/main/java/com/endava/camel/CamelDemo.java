package com.endava.camel;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

public class CamelDemo {
    public static void main(String[] args) throws Exception {
//        CamelContext context = new DefaultCamelContext();
//        ConnectionFactory connectionFactory =
//                new ActiveMQConnectionFactory("tcp://localhost:61616");
//        context.addComponent("activemq",
//                JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
//        context.addRoutes(new RouteBuilder() {
//            @Override
//            public void configure() throws Exception {
//                from("activemq:request.q")
//                        .log("Moving message: ${body}")
//                        .to("activemq:response.q");
//            }
//        });
//        context.addRoutes(new RouteBuilder() {
//            @Override
//            public void configure() throws Exception {
//                from("file:input")
//                        .log("Moving file: ${body}")
//                        .to("file:output");
//            }
//        });
//        context.start();
//        System.out.println("Press any key to stop...");
//        System.in.read();
//        context.stop();
    }
}
