package edu.poc.prometheus.infra.restapi.controller;

import edu.poc.prometheus.core.enumerator.ConsentStatus;
import edu.poc.prometheus.core.metric.CustomRegister;
import edu.poc.prometheus.core.metric.tag.TagValue;
import edu.poc.prometheus.core.usecase.CountConsentUseCase;
import edu.poc.prometheus.core.usecase.CreateConsentUseCase;
import edu.poc.prometheus.infra.restapi.request.ConsentRequest;
import edu.poc.prometheus.infra.restapi.response.ConsentCountResponse;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import java.util.function.Supplier;

import static edu.poc.prometheus.application.mapper.ConsentMapper.mapFromConsentRequest;
import static edu.poc.prometheus.core.metric.enumerator.GaugeMetric.CONSENT_COUNT_BY_STATUS;
import static edu.poc.prometheus.core.metric.enumerator.SummaryMetric.CONSENT_CREATE_HTTP_SUMMARY;
import static edu.poc.prometheus.core.metric.enumerator.TimerMetric.CONSENT_CREATE_HTTP_TIMER;
import static edu.poc.prometheus.core.metric.enumerator.TimerMetric.CONSENT_CREATE_HTTP_TIMER_DESCRIPTION;
import static edu.poc.prometheus.core.metric.tag.TagUtil.buildTags;

@RequiredArgsConstructor
@RequestMapping("/v1/consents")
@RestController
public class ConsentController {

    private final MeterRegistry meterRegistry;

    private final CustomRegister customRegister;

    private final CreateConsentUseCase createConsentUseCase;

    private final CountConsentUseCase countConsentUseCase;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/count")
    public ConsentCountResponse countByStatus(@RequestParam final ConsentStatus status) {
        val total = countConsentUseCase.countByStatus(status);

        customRegister.gauge(CONSENT_COUNT_BY_STATUS, () -> total, Tags.of("consent_status", status.name()));

        return new ConsentCountResponse(total);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody final ConsentRequest consentRequest, final HttpServletRequest servletRequest) {
        val timer = customRegister.buildTimer(CONSENT_CREATE_HTTP_TIMER, CONSENT_CREATE_HTTP_TIMER_DESCRIPTION, buildTags(buildTagsToTimer()));

        customRegister.buildSummary(CONSENT_CREATE_HTTP_SUMMARY, buildTags(buildTagsToSummary()))
            .record(servletRequest.getContentLengthLong());

        timer.record(() -> {
            createConsentUseCase.process(mapFromConsentRequest(consentRequest));
        });
    }

    private static TagValue buildTagsToTimer() {
        return new TagValue("consent_core", "timer", "post", "http", "controller");
    }

    private static TagValue buildTagsToSummary() {
        return new TagValue("consent_core", "summary", "post", "http", "controller");
    }

    private void gauge(final Supplier<Number> totalSupplier, final ConsentStatus consentStatus) {

        Gauge.builder(CONSENT_COUNT_BY_STATUS.getMetricName(), totalSupplier)
            .description(CONSENT_COUNT_BY_STATUS.getDescription())
             .tags("consent_status", consentStatus.name())
            .register(meterRegistry);
    }

    private void summary() {

        DistributionSummary.builder(CONSENT_CREATE_HTTP_SUMMARY.getMetricName())
            .description(CONSENT_CREATE_HTTP_SUMMARY.getDescription())
            .baseUnit("bytes")
            .tag("entrypoint", "http")
            .register(meterRegistry);
    }
}
