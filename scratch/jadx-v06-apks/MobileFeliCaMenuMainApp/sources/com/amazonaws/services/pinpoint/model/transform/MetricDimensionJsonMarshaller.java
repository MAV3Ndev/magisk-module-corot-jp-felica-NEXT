package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.MetricDimension;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class MetricDimensionJsonMarshaller {
    private static MetricDimensionJsonMarshaller instance;

    MetricDimensionJsonMarshaller() {
    }

    public void marshall(MetricDimension metricDimension, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (metricDimension.getComparisonOperator() != null) {
            String comparisonOperator = metricDimension.getComparisonOperator();
            awsJsonWriter.name("ComparisonOperator");
            awsJsonWriter.value(comparisonOperator);
        }
        if (metricDimension.getValue() != null) {
            Double value = metricDimension.getValue();
            awsJsonWriter.name("Value");
            awsJsonWriter.value(value);
        }
        awsJsonWriter.endObject();
    }

    public static MetricDimensionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new MetricDimensionJsonMarshaller();
        }
        return instance;
    }
}
