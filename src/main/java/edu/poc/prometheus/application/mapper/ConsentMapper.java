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
        return new Consent(consentEvent.consentId(),
            ConsentStatus.valueOf(consentEvent.status()),
            consentEvent.rejectReason(),
            ConsentType.valueOf(consentEvent.consentType()),
            consentEvent.metadata());
    }

    public static Consent mapFromConsentRequest(final ConsentRequest consentRequest) {
        return new Consent(consentRequest.consentId(),
            ConsentStatus.valueOf(consentRequest.status()),
            consentRequest.rejectReason(),
            ConsentType.valueOf(consentRequest.consentType()),
            consentRequest.metadata());
    }

}
