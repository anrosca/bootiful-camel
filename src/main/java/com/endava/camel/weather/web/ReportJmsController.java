package com.endava.camel.weather.web;

import com.endava.camel.weather.report.Report;
import com.endava.camel.weather.service.ReportGenerationEventBroadcaster;
import com.endava.camel.weather.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportJmsController {
    private final ReportService reportService;
    private final ReportGenerationEventBroadcaster eventBroadcaster;

    public ResponseMessage generateReports(RequestMessage request) {
        eventBroadcaster.generateReportsByTriggerType(request.getReportType());
        return ResponseMessage.ok();
    }

    public ResponseMessage getAll(RequestMessage request) {
        List<Report> reports = reportService.getAll();
        return ResponseMessage.ok(reports);
    }

    public ResponseMessage getById(RequestMessage request) {
        Report report = reportService.getById(request.getReportId());
        return ResponseMessage.ok(report);
    }

    public ResponseMessage downloadReport(RequestMessage request) {
        Report report = reportService.getById(request.getReportId());
        return ResponseMessage.ok(report);
    }

    public ResponseMessage deleteById(RequestMessage request) {
        reportService.deleteById(request.getReportId());
        return ResponseMessage.ok();
    }
}
