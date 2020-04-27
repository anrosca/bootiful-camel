package com.endava.camel.weather.web;

import com.endava.camel.weather.report.Report;
import com.endava.camel.weather.service.ReportGenerationEventBroadcaster;
import com.endava.camel.weather.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

import static com.endava.camel.weather.web.OperationType.GET_ALL_REPORTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Testcontainers
public class ReportJmsListenerTest {

    private static final String REQUEST_QUEUE = "report_request.q";
    private static final String RESPONSE_QUEUE = "report_response.q";

    @Container
    public static GenericContainer<?> activeMqContainer = new GenericContainer<>("rmohr/activemq:latest")
            .withExposedPorts(61616);

    @MockBean
    private ReportService reportService;

    @MockBean
    private ReportGenerationEventBroadcaster eventBroadcaster;

    @Autowired
    private JmsTemplate jmsTemplate;

    @DynamicPropertySource
    static void activeMqProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.activemq.broker-url", () -> "tcp://localhost:" + activeMqContainer.getMappedPort(61616));
    }

    @Test
    public void test() throws JsonProcessingException {
        Report expectedReport = Report.builder().fileName("report_1.pdf").build();
        when(reportService.getAll()).thenReturn(List.of(expectedReport));
        RequestMessage request = RequestMessage.builder().operationType(GET_ALL_REPORTS).build();

        jmsTemplate.convertAndSend(REQUEST_QUEUE, request);

        ResponseMessage response = (ResponseMessage) jmsTemplate.receiveAndConvert(RESPONSE_QUEUE);
        assertEquals(ResponseStatus.OK, response.getStatus());
        assertEquals(List.of(expectedReport), fromJson(response.getPayload()));

    }

    private List<Report> fromJson(Object payload) throws JsonProcessingException {
        return new ObjectMapper().readValue(payload.toString(), new TypeReference<>() {
        });
    }
}
