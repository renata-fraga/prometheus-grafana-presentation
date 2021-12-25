package edu.poc.prometheus.infra.metric.enumerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum MetricName {

    GAUGE_COUNT_BY_STATUS("consent_core_count_by_status", "gauge for consent by status"),
    COUNT_VALID_EVENT("consent_core_consent_event_processor", "consent events from listener");

    private final String metricName;

    private final String description;
}
