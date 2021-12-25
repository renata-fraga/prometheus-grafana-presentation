package edu.poc.prometheus.core.usecase;

import edu.poc.prometheus.core.enumerator.ConsentStatus;
import edu.poc.prometheus.infra.database.repository.ConsentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CountConsentUseCase {

    private final ConsentRepository consentRepository;

    public long countByStatus(final ConsentStatus status) {
        return consentRepository.countByStatus(status);
    }

}
