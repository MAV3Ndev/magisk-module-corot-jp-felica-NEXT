package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EventsBatch;
import com.amazonaws.services.pinpoint.model.EventsRequest;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class EventsRequestJsonMarshaller {
    private static EventsRequestJsonMarshaller instance;

    EventsRequestJsonMarshaller() {
    }

    public void marshall(EventsRequest eventsRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (eventsRequest.getBatchItem() != null) {
            Map<String, EventsBatch> batchItem = eventsRequest.getBatchItem();
            awsJsonWriter.name("BatchItem");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, EventsBatch> entry : batchItem.entrySet()) {
                EventsBatch value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    EventsBatchJsonMarshaller.getInstance().marshall(value, awsJsonWriter);
                }
            }
            awsJsonWriter.endObject();
        }
        awsJsonWriter.endObject();
    }

    public static EventsRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EventsRequestJsonMarshaller();
        }
        return instance;
    }
}
