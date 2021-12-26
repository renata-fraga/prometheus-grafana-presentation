package edu.poc.prometheus.core.domain;

import edu.poc.prometheus.core.enumerator.ConsentStatus;
import edu.poc.prometheus.core.enumerator.ConsentType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Getter
@Builder
@Document(collection = "consents")
public class Consent {

    @Id
    private final String id;

    @Field("consent_id")
    private final String consentId;

    private final ConsentStatus status;

    @Field("reject_reason")
    private final String rejectReason;

    @Field("consent_type")
    private final ConsentType consentType;

    private final Map<String, Object> metadata;

}