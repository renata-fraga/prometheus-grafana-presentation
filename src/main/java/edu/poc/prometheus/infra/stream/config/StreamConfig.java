package edu.poc.prometheus.infra.stream.config;

import edu.poc.prometheus.application.processor.ConsentProcessor;
import edu.poc.prometheus.infra.stream.event.ConsentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StreamConfig {

    private final ConsentProcessor consentProcessor;

    @Bean
    public Consumer<ConsentEvent> consentEventListener() {
        return consentProcessor::process;
    }

}
