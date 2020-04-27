package com.endava.camel.weather.web;

import com.endava.camel.weather.report.ReportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestMessage {

    private OperationType operationType;

    private Long reportId;

    private ReportType reportType;
}
