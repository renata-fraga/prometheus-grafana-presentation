package edu.poc.prometheus.infra.stream.config;

import edu.poc.prometheus.application.processor.ConsentProcessor;
import edu.poc.prometheus.infra.stream.event.ConsentEvent;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

import static edu.poc.prometheus.core.metric.enumerator.TimerMetric.*;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StreamConfig {

    private final ConsentProcessor consentProcessor;

    @Timed(value = CONSENT_EVENT_CREATE_KAFKA, description = CONSENT_EVENT_CREATE_KAFKA_DESCRIPTION)
    @Bean
    public Consumer<ConsentEvent> consentEventListener() {
        return consentProcessor::process;
    }

}
