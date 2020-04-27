package com.endava.camel.weather.service;

import com.endava.camel.weather.report.ReportConfig;
import com.endava.camel.weather.report.ReportGenerationRequest;
import com.endava.camel.weather.report.ReportType;
import com.endava.camel.weather.repository.ReportConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ReportGenerationEventBroadcaster {

    private final ReportConfigRepository reportConfigRepository;
    private final JmsTemplate jmsTemplate;

    public void generateReportsByTriggerType(ReportType triggerType) {
        reportConfigRepository.findByTriggerType(triggerType)
                .forEach(this::generateReport);
    }

    private void generateReport(ReportConfig config) {
        ReportGenerationRequest request = ReportGenerationRequest.builder()
                .city(config.getCity().getName())
                .date(LocalDate.now().toString())
                .build();
        jmsTemplate.convertAndSend("REPORTS.Q", request);
    }
}
