package com.endava.camel.weather.web;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import static com.endava.camel.weather.web.OperationType.*;

@Component
public class ReportJmsListener {

    private static final Function<RequestMessage, ResponseMessage> NULL_OPERATION = (request) -> null;

    private final Map<OperationType, Function<RequestMessage, ResponseMessage>> OPERATIONS = new EnumMap<>(OperationType.class);
    private final ReportJmsController reportJmsController;

    public ReportJmsListener(ReportJmsController reportJmsController) {
        this.reportJmsController = reportJmsController;
    }

    @PostConstruct
    private void init() {
        OPERATIONS.put(DELETE_REPORT, reportJmsController::deleteById);
        OPERATIONS.put(GET_ALL_REPORTS, reportJmsController::getAll);
        OPERATIONS.put(GET_REPORT_BY_ID, reportJmsController::getById);
        OPERATIONS.put(DOWNLOAD_REPORT, reportJmsController::downloadReport);
        OPERATIONS.put(GENERARE_REPORTS, reportJmsController::generateReports);
    }

    @JmsListener(destination = "report_request.q")
    @SendTo("report_response.q")
    public ResponseMessage onMessage(RequestMessage requestMessage) {
        return OPERATIONS.getOrDefault(requestMessage.getOperationType(), NULL_OPERATION)
                .apply(requestMessage);
    }
}
