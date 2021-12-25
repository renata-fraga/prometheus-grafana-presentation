package edu.poc.prometheus.infra.stream.event;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

public record ConsentEvent(
    @NotBlank String consentId,
    @NotBlank String status,
    String rejectReason,
    @NotBlank String consentType,
    @NotNull Map<String, Object> metadata) {

}
