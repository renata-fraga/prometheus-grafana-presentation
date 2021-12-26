package edu.poc.prometheus.core.usecase;


import edu.poc.prometheus.core.domain.Consent;
import edu.poc.prometheus.core.metric.CustomRegister;
import edu.poc.prometheus.core.metric.tag.TagValue;
import edu.poc.prometheus.infra.database.repository.ConsentRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

import static edu.poc.prometheus.core.metric.enumerator.TimerMetric.CONSENT_EXIST_BY_CONSENTID;
import static edu.poc.prometheus.core.metric.enumerator.TimerMetric.CONSENT_EXIST_BY_CONSENTID_DESCRIPTION;
import static edu.poc.prometheus.core.metric.tag.TagUtil.buildTags;

@RequiredArgsConstructor
@Service
public class CreateConsentUseCase {

    private final MeterRegistry meterRegistry;

    private final CustomRegister customRegister;

    private final ConsentRepository consentRepository;

    public void process(final Consent consent) {
        val exists = new AtomicBoolean(false);
        val timer = customRegister.buildTimer(CONSENT_EXIST_BY_CONSENTID, CONSENT_EXIST_BY_CONSENTID_DESCRIPTION,
            buildTags(buildTagsToTimer()));

        timer.record(() -> {
            exists.set(consentRepository.existsByConsentId(consent.consentId()));
        });

        if (!exists.get()) {
            consentRepository.save(consent);
        }
    }

    private void timer() {

        Timer.builder(CONSENT_EXIST_BY_CONSENTID)
            .description(CONSENT_EXIST_BY_CONSENTID_DESCRIPTION)
            .tags("service_name", "consent_core")
            .register(meterRegistry);
    }

    private static TagValue buildTagsToTimer() {
        return new TagValue("consent_core", "timer", "post", "http", "controller");
    }

}
