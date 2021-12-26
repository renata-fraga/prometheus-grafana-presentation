package edu.poc.prometheus.core.metric;

import edu.poc.prometheus.core.metric.enumerator.CounterMetric;
import edu.poc.prometheus.core.metric.enumerator.GaugeMetric;
import edu.poc.prometheus.core.metric.enumerator.SummaryMetric;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;

import java.util.function.Supplier;

public interface CustomMeterRegistry {

    void count(final CounterMetric metric, final Iterable<Tag> tags);

    void gauge(final GaugeMetric metric, Supplier<Number> supplier, final Iterable<Tag> tags);

    Timer buildTimer(final String name, final String description, final Iterable<Tag> tags);

    DistributionSummary buildSummary(final SummaryMetric metric, final Iterable<Tag> tags);

}
