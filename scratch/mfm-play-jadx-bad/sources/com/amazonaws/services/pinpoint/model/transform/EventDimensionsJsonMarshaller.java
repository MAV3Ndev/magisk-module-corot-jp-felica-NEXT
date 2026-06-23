package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.AttributeDimension;
import com.amazonaws.services.pinpoint.model.EventDimensions;
import com.amazonaws.services.pinpoint.model.MetricDimension;
import com.amazonaws.services.pinpoint.model.SetDimension;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class EventDimensionsJsonMarshaller {
    private static EventDimensionsJsonMarshaller instance;

    EventDimensionsJsonMarshaller() {
    }

    public void marshall(EventDimensions eventDimensions, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (eventDimensions.getAttributes() != null) {
            Map<String, AttributeDimension> attributes = eventDimensions.getAttributes();
            awsJsonWriter.name("Attributes");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, AttributeDimension> entry : attributes.entrySet()) {
                AttributeDimension value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    AttributeDimensionJsonMarshaller.getInstance().marshall(value, awsJsonWriter);
                }
            }
            awsJsonWriter.endObject();
        }
        if (eventDimensions.getEventType() != null) {
            SetDimension eventType = eventDimensions.getEventType();
            awsJsonWriter.name("EventType");
            SetDimensionJsonMarshaller.getInstance().marshall(eventType, awsJsonWriter);
        }
        if (eventDimensions.getMetrics() != null) {
            Map<String, MetricDimension> metrics = eventDimensions.getMetrics();
            awsJsonWriter.name("Metrics");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, MetricDimension> entry2 : metrics.entrySet()) {
                MetricDimension value2 = entry2.getValue();
                if (value2 != null) {
                    awsJsonWriter.name(entry2.getKey());
                    MetricDimensionJsonMarshaller.getInstance().marshall(value2, awsJsonWriter);
                }
            }
            awsJsonWriter.endObject();
        }
        awsJsonWriter.endObject();
    }

    public static EventDimensionsJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EventDimensionsJsonMarshaller();
        }
        return instance;
    }
}
