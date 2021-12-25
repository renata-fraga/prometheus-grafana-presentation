package edu.poc.prometheus.core.usecase;


import edu.poc.prometheus.core.domain.Consent;
import edu.poc.prometheus.infra.database.repository.ConsentRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CreateConsentUseCase {

    private final MeterRegistry meterRegistry;

    private final ConsentRepository consentRepository;

    private final Timer timer;

    public CreateConsentUseCase(final MeterRegistry meterRegistry, final ConsentRepository consentRepository) {
        this.meterRegistry = meterRegistry;
        this.consentRepository = consentRepository;
        this.timer = buildTimer();
    }

    public void process(final Consent consent) {
        final AtomicBoolean exists = new AtomicBoolean(false);

        timer.record(() -> {
            exists.set(consentRepository.existsByConsentId(consent.consentId()));
        });

        if (!exists.get()) {
            consentRepository.save(consent);
        }
    }

    private Timer buildTimer() {
        return Timer.builder("consent_core_exists_by_consent_id")
            .description("timer exists by consentId query")
            //.tags("service", "create", "create_timer")
            .register(meterRegistry);
    }

}
