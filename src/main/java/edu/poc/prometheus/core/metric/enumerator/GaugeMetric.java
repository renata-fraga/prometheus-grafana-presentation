package edu.poc.prometheus.core.metric.enumerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum GaugeMetric {

    CONSENT_COUNT_BY_STATUS("consent_count_by_status", "gauge of the amount of consents by status");

    private final String metricName;

    private final String description;
}
