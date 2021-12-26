package edu.poc.prometheus.infra.metric.impl;

import edu.poc.prometheus.core.metric.CustomRegister;
import edu.poc.prometheus.core.metric.enumerator.CounterMetric;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomRegisterImpl implements CustomRegister {

    private final MeterRegistry meterRegistry;

    @Override
    public void count(final CounterMetric metric, final String[] tags) {
        Counter.builder(metric.getMetricName())
            .description(metric.getDescription())
            .tags(tags)
            .register(meterRegistry)
            .increment();
    }

}
