package edu.poc.prometheus.core.metric.enumerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum SummaryMetric {
    CONSENT_CREATE_HTTP_SUMMARY("consent_create_http_summary", "distribution summary of the size of the number of requests");

    private final String metricName;

    private final String description;
}
