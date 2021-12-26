package edu.poc.prometheus.infra.metric.impl;

import edu.poc.prometheus.core.metric.CustomRegister;
import edu.poc.prometheus.core.metric.enumerator.CounterMetric;
import edu.poc.prometheus.core.metric.enumerator.GaugeMetric;
import edu.poc.prometheus.core.metric.enumerator.SummaryMetric;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Component
public class CustomRegisterImpl implements CustomRegister {

    private final MeterRegistry meterRegistry;

    @Override
    public void count(final CounterMetric metric, final Iterable<Tag> tags) {
        Counter.builder(metric.getMetricName())
            .description(metric.getDescription())
            .tags(tags)
            .register(meterRegistry)
            .increment();
    }

    @Override
    public void gauge(final GaugeMetric metric, Supplier<Number> supplier, final Iterable<Tag> tags) {
        Gauge.builder(metric.getMetricName(), supplier)
            .description(metric.getDescription())
            .tags(tags)
            .register(meterRegistry);
    }

    @Override
    public Timer buildTimer(final String name, final String description, final Iterable<Tag> tags) {
        return Timer.builder(name)
            .description(description)
            .tags(tags)
            .register(meterRegistry);
    }

    @Override
    public DistributionSummary buildSummary(final SummaryMetric metric, final Iterable<Tag> tags) {
        return DistributionSummary.builder(metric.getMetricName())
            .description(metric.getDescription())
            .baseUnit(metric.getBaseUnit())
            .tags(tags)
            .register(meterRegistry);
    }

}
