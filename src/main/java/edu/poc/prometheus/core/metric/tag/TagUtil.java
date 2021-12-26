package edu.poc.prometheus.core.metric.tag;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TagUtil {

    private static final String SERVICE_NAME_KEY = "service_name";
    private static final String METRIC_NAME_KEY = "metric_name";
    private static final String OPERATION_KEY = "operation";
    private static final String ENTRYPOINT_KEY = "entrypoint";
    private static final String LAYER_KEY = "layer";

    public static Iterable<Tag> buildTags(final TagValue tagValue) {
        return Tags.of(
            SERVICE_NAME_KEY, tagValue.serviceName(),
            METRIC_NAME_KEY, tagValue.metricType(),
            OPERATION_KEY, tagValue.operation(),
            ENTRYPOINT_KEY, tagValue.entrypoint(),
            LAYER_KEY, tagValue.layer());
    }

}