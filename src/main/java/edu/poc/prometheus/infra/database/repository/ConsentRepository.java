package edu.poc.prometheus.infra.database.repository;

import edu.poc.prometheus.core.domain.Consent;
import edu.poc.prometheus.core.enumerator.ConsentStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConsentRepository extends MongoRepository<Consent, String> {

    boolean existsByConsentId(final String consentId);

    long countByStatus(final ConsentStatus status);

}