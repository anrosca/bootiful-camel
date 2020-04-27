package com.endava.camel.config;

import com.endava.camel.config.properties.EmailProperties;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;

@Configuration
@EnableConfigurationProperties(EmailProperties.class)
public class CamelConfig {

    @Bean
    public JsonDataFormat jsonDataFormat() {
        return new JsonDataFormat();
    }

    @Bean
    public JmsComponent jms(ConnectionFactory connectionFactory) {
        return JmsComponent.jmsComponentAutoAcknowledge(connectionFactory);
    }
}
