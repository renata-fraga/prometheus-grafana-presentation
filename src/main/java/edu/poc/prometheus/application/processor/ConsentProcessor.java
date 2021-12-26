package edu.poc.prometheus.application.processor;

import edu.poc.prometheus.application.mapper.ConsentMapper;
import edu.poc.prometheus.application.validation.ObjectValidator;
import edu.poc.prometheus.core.metric.CustomMeterRegistry;
import edu.poc.prometheus.core.metric.tag.TagValue;
import edu.poc.prometheus.core.usecase.CreateConsentUseCase;
import edu.poc.prometheus.infra.stream.event.ConsentEvent;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static edu.poc.prometheus.core.metric.enumerator.CounterMetric.CONSENT_EVENT_INVALID;
import static edu.poc.prometheus.core.metric.enumerator.CounterMetric.CONSENT_EVENT_VALID;
import static edu.poc.prometheus.core.metric.tag.TagUtil.buildTags;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConsentProcessor {

    private final MeterRegistry meterRegistry;

    private final CustomMeterRegistry customMeterRegistry;

    private final CreateConsentUseCase createConsentUseCase;

    private final ObjectValidator<ConsentEvent> objectValidator;

    public void process(final ConsentEvent consentEvent) {
        log.info("method: process | params: consentEvent - {}", consentEvent);

        try {
            objectValidator.validateFields(consentEvent);

            customMeterRegistry.count(CONSENT_EVENT_VALID, buildTags(buildTagsFromEvent()));
        } catch (RuntimeException ex) {
            incrementFail();
        }

        createConsentUseCase.process(ConsentMapper.mapFromConsentEvent(consentEvent));
    }

    private void incrementFail() {
        Counter.builder(CONSENT_EVENT_INVALID.getMetricName())
            .description(CONSENT_EVENT_INVALID.getDescription())
            .tag("counter", "invalid_consent_events")
            .register(meterRegistry)
            .increment();
    }

    private static TagValue buildTagsFromEvent() {
        return new TagValue("consent_core", "counter", "create", "kafka", "processor");
    }
}
