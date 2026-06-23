package com.amazonaws.services.pinpointanalytics.model.transform;

import com.amazonaws.services.pinpointanalytics.model.Event;
import com.amazonaws.services.pinpointanalytics.model.Session;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class EventJsonMarshaller {
    private static EventJsonMarshaller instance;

    EventJsonMarshaller() {
    }

    public void marshall(Event event, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (event.getEventType() != null) {
            String eventType = event.getEventType();
            awsJsonWriter.name("eventType");
            awsJsonWriter.value(eventType);
        }
        if (event.getTimestamp() != null) {
            String timestamp = event.getTimestamp();
            awsJsonWriter.name("timestamp");
            awsJsonWriter.value(timestamp);
        }
        if (event.getSession() != null) {
            Session session = event.getSession();
            awsJsonWriter.name("session");
            SessionJsonMarshaller.getInstance().marshall(session, awsJsonWriter);
        }
        if (event.getVersion() != null) {
            String version = event.getVersion();
            awsJsonWriter.name("version");
            awsJsonWriter.value(version);
        }
        if (event.getAttributes() != null) {
            Map<String, String> attributes = event.getAttributes();
            awsJsonWriter.name("attributes");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    awsJsonWriter.value(value);
                }
            }
            awsJsonWriter.endObject();
        }
        if (event.getMetrics() != null) {
            Map<String, Double> metrics = event.getMetrics();
            awsJsonWriter.name("metrics");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, Double> entry2 : metrics.entrySet()) {
                Double value2 = entry2.getValue();
                if (value2 != null) {
                    awsJsonWriter.name(entry2.getKey());
                    awsJsonWriter.value(value2);
                }
            }
            awsJsonWriter.endObject();
        }
        awsJsonWriter.endObject();
    }

    public static EventJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EventJsonMarshaller();
        }
        return instance;
    }
}
