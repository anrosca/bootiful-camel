package com.endava.camel.weather.listener;

import com.endava.camel.weather.report.ReportGenerationRequest;
import com.endava.camel.weather.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReportContentListener {

    private final ReportService reportService;

    @JmsListener(destination = "REPORTS.Q", concurrency = "3-5")
    public void onReportContentRequest(ReportGenerationRequest request) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Generating content for report {}", request.getCity());
        reportService.generateReport(request);
        log.info("Content for report {} was generated", request.getCity());
    }
}
