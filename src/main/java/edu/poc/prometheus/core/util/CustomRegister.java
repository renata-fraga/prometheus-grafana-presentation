package edu.poc.prometheus.core.util;

import edu.poc.prometheus.infra.metric.enumerator.MetricName;

public interface CustomRegister {

    void count(final MetricName metric, final String[] tags);

}
