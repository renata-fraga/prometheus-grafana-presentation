package edu.poc.prometheus.core.usecase;


import edu.poc.prometheus.core.domain.Consent;
import edu.poc.prometheus.infra.database.repository.ConsentRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

import static edu.poc.prometheus.core.metric.enumerator.TimerMetric.CONSENT_EXIST_BY_CONSENTID;
import static edu.poc.prometheus.core.metric.enumerator.TimerMetric.CONSENT_EXIST_BY_CONSENTID_DESCRIPTION;

@RequiredArgsConstructor
@Service
public class CreateConsentUseCase {

    private final MeterRegistry meterRegistry;

    private final ConsentRepository consentRepository;

    public void process(final Consent consent) {
        val exists = new AtomicBoolean(false);
        val timer = buildTimer();

        timer.record(() -> {
            exists.set(consentRepository.existsByConsentId(consent.consentId()));
        });

        if (!exists.get()) {
            consentRepository.save(consent);
        }
    }

    private Timer buildTimer() {
        return Timer.builder(CONSENT_EXIST_BY_CONSENTID)
            .description(CONSENT_EXIST_BY_CONSENTID_DESCRIPTION)
            //.tags("service", "create", "create_timer")
            .register(meterRegistry);
    }

}
