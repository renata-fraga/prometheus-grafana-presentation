package edu.poc.prometheus.infra.restapi.request;

import java.util.Map;

public record ConsentRequest(String consentId, String status, String rejectReason, String consentType, Map<String, Object> metadata) {

}
