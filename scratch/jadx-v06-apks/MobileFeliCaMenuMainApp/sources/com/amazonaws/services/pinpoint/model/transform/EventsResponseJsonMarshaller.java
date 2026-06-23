package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EventsResponse;
import com.amazonaws.services.pinpoint.model.ItemResponse;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class EventsResponseJsonMarshaller {
    private static EventsResponseJsonMarshaller instance;

    EventsResponseJsonMarshaller() {
    }

    public void marshall(EventsResponse eventsResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (eventsResponse.getResults() != null) {
            Map<String, ItemResponse> results = eventsResponse.getResults();
            awsJsonWriter.name("Results");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, ItemResponse> entry : results.entrySet()) {
                ItemResponse value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    ItemResponseJsonMarshaller.getInstance().marshall(value, awsJsonWriter);
                }
            }
            awsJsonWriter.endObject();
        }
        awsJsonWriter.endObject();
    }

    public static EventsResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EventsResponseJsonMarshaller();
        }
        return instance;
    }
}
