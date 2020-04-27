package com.endava.camel.weather.repository;

import com.endava.camel.weather.report.ReportConfig;
import com.endava.camel.weather.report.ReportType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReportConfigRepository extends CrudRepository<ReportConfig, Long> {

    List<ReportConfig> findByTriggerType(ReportType triggerType);
}