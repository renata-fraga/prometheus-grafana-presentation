package edu.poc.prometheus.infra.restapi.controller;

import edu.poc.prometheus.application.mapper.ConsentMapper;
import edu.poc.prometheus.core.enumerator.ConsentStatus;
import edu.poc.prometheus.core.usecase.CountConsentUseCase;
import edu.poc.prometheus.core.usecase.CreateConsentUseCase;
import edu.poc.prometheus.infra.restapi.request.ConsentRequest;
import edu.poc.prometheus.infra.restapi.response.ConsentCountResponse;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
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

import static edu.poc.prometheus.core.metric.enumerator.GaugeMetric.CONSENT_COUNT_BY_STATUS;
import static edu.poc.prometheus.core.metric.enumerator.SummaryMetric.CONSENT_CREATE_HTTP_SUMMARY;
import static edu.poc.prometheus.core.metric.enumerator.TimerMetric.CONSENT_CREATE_HTTP_TIMER;
import static edu.poc.prometheus.core.metric.enumerator.TimerMetric.CONSENT_CREATE_HTTP_TIMER_DESCRIPTION;

@RequiredArgsConstructor
@RequestMapping("/v1/consents")
@RestController
public class ConsentController {

    private final MeterRegistry meterRegistry;

    private final CreateConsentUseCase createConsentUseCase;

    private final CountConsentUseCase countConsentUseCase;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/count")
    public ConsentCountResponse countByStatus(@RequestParam final ConsentStatus status) {
        val total = countConsentUseCase.countByStatus(status);

        Gauge.builder(CONSENT_COUNT_BY_STATUS.getMetricName(), () -> total)
            .description(CONSENT_COUNT_BY_STATUS.getDescription())
            // .tags("consent_core", "status_gauge", status.name())
            .register(meterRegistry);

        return new ConsentCountResponse(total);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@RequestBody final ConsentRequest consentRequest, final HttpServletRequest servletRequest) {

        val timer = Timer.builder(CONSENT_CREATE_HTTP_TIMER)
            .description(CONSENT_CREATE_HTTP_TIMER_DESCRIPTION)
            .tag("input", "create_http")
            .register(meterRegistry);

        DistributionSummary.builder(CONSENT_CREATE_HTTP_SUMMARY.getMetricName())
            .description(CONSENT_CREATE_HTTP_SUMMARY.getDescription())
            .baseUnit("bytes")
            .tag("input", "create_http")
            .register(meterRegistry)
            .record(servletRequest.getContentLengthLong());

        timer.record(() -> {
            createConsentUseCase.process(ConsentMapper.mapFromConsentRequest(consentRequest));
        });
    }

}
