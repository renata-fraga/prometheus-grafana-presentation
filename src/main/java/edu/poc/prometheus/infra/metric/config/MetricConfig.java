package edu.poc.prometheus.infra.metric.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class MetricConfig {

    private final MeterRegistry meterRegistry;

    @Bean
    public TimedAspect timeAspect() {
        return new TimedAspect(meterRegistry);
    }
}
