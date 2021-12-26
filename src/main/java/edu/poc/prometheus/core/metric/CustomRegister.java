package edu.poc.prometheus.core.metric;

import edu.poc.prometheus.core.metric.enumerator.CounterMetric;

public interface CustomRegister {

    void count(final CounterMetric metric, final String[] tags);

}
