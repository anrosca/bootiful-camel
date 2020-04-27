package com.endava.camel.weather.repository;

import com.endava.camel.weather.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
