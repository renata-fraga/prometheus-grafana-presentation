package edu.poc.prometheus.core.domain;

import edu.poc.prometheus.core.enumerator.ConsentStatus;
import edu.poc.prometheus.core.enumerator.ConsentType;
import lombok.Builder;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Builder
@Document(collection = "consents")
public record Consent(@Id @Setter String id,
                      @Field("consent_id") String consentId,
                      ConsentStatus status,
                      @Field("reject_reason") String rejectReason,
                      @Field("consent_type") ConsentType consentType,
                      Map<String, Object> metadata) {

    public Consent(String consentId, ConsentStatus status, String rejectReason, ConsentType consentType, Map<String, Object> metadata) {
        this(null, consentId, status, rejectReason, consentType, metadata);
    }

}