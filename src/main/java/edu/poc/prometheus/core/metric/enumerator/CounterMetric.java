package edu.poc.prometheus.core.metric.enumerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum CounterMetric {

    CONSENT_EVENT_VALID("consent_event_valid", "valid consent event counter"),
    CONSENT_EVENT_INVALID("consent_event_invalid", "invalid consent event counter"),
    CONSENT_SAVED("consent_saved_by_status", "count all consents by status");

    private final String metricName;

    private final String description;
}
