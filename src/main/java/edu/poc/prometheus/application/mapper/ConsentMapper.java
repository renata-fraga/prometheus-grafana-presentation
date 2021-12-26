package edu.poc.prometheus.application.mapper;

import edu.poc.prometheus.core.domain.Consent;
import edu.poc.prometheus.core.enumerator.ConsentStatus;
import edu.poc.prometheus.core.enumerator.ConsentType;
import edu.poc.prometheus.infra.restapi.request.ConsentRequest;
import edu.poc.prometheus.infra.stream.event.ConsentEvent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConsentMapper {

    public static Consent mapFromConsentEvent(final ConsentEvent consentEvent) {
        return Consent.builder()
            .consentId(consentEvent.consentId())
            .status(ConsentStatus.valueOf(consentEvent.status()))
            .rejectReason(consentEvent.rejectReason())
            .consentType(ConsentType.valueOf(consentEvent.consentType()))
            .metadata(consentEvent.metadata())
            .build();
    }

    public static Consent mapFromConsentRequest(final ConsentRequest consentRequest) {
        return Consent.builder()
            .consentId(consentRequest.consentId())
            .status(ConsentStatus.valueOf(consentRequest.status()))
            .rejectReason(consentRequest.rejectReason())
            .consentType(ConsentType.valueOf(consentRequest.consentType()))
            .metadata(consentRequest.metadata())
            .build();
    }

}
