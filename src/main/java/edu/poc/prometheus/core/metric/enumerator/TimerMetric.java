package edu.poc.prometheus.core.metric.enumerator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimerMetric {

    public static final String CONSENT_EVENT_CREATE_KAFKA = "consent_event_create_kafka";
    public static final String CONSENT_EVENT_CREATE_KAFKA_DESCRIPTION = "processing time of consent creation events via kafka";

    public static final String CONSENT_CREATE_HTTP_TIMER = "consent_create_http_timer";
    public static final String CONSENT_CREATE_HTTP_TIMER_DESCRIPTION = "consent creation processing time via http";

    public static final String CONSENT_EXIST_BY_CONSENTID = "consent_exist_by_consentId";
    public static final String CONSENT_EXIST_BY_CONSENTID_DESCRIPTION = "consultation time of consent by id in the database";

}
