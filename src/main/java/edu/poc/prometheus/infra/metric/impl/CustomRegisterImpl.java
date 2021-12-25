package edu.poc.prometheus.infra.metric.impl;

import edu.poc.prometheus.core.util.CustomRegister;
import edu.poc.prometheus.infra.metric.enumerator.MetricName;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomRegisterImpl implements CustomRegister {

    private final MeterRegistry meterRegistry;

    @Override
    public void count(final MetricName metric, final String[] tags) {
        Counter.builder(metric.getMetricName())
            .description(metric.getDescription())
            .tags(tags)
            .register(meterRegistry)
            .increment();
    }

}
