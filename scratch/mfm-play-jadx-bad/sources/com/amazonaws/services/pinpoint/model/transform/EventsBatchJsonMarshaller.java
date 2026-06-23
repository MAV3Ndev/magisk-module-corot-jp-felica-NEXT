package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.Event;
import com.amazonaws.services.pinpoint.model.EventsBatch;
import com.amazonaws.services.pinpoint.model.PublicEndpoint;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class EventsBatchJsonMarshaller {
    private static EventsBatchJsonMarshaller instance;

    EventsBatchJsonMarshaller() {
    }

    public void marshall(EventsBatch eventsBatch, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (eventsBatch.getEndpoint() != null) {
            PublicEndpoint endpoint = eventsBatch.getEndpoint();
            awsJsonWriter.name("Endpoint");
            PublicEndpointJsonMarshaller.getInstance().marshall(endpoint, awsJsonWriter);
        }
        if (eventsBatch.getEvents() != null) {
            Map<String, Event> events = eventsBatch.getEvents();
            awsJsonWriter.name("Events");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, Event> entry : events.entrySet()) {
                Event value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    EventJsonMarshaller.getInstance().marshall(value, awsJsonWriter);
                }
            }
            awsJsonWriter.endObject();
        }
        awsJsonWriter.endObject();
    }

    public static EventsBatchJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EventsBatchJsonMarshaller();
        }
        return instance;
    }
}
