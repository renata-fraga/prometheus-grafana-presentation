package edu.poc.prometheus.core.metric.tag;

public record TagValue(String serviceName, String metricType, String operation, String entrypoint,
                       String layer) {

}
