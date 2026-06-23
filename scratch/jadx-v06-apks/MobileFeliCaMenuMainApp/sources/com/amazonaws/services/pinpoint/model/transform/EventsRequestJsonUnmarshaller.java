package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EventsRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class EventsRequestJsonUnmarshaller implements Unmarshaller<EventsRequest, JsonUnmarshallerContext> {
    private static EventsRequestJsonUnmarshaller instance;

    EventsRequestJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public EventsRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        EventsRequest eventsRequest = new EventsRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("BatchItem")) {
                eventsRequest.setBatchItem(new MapUnmarshaller(EventsBatchJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return eventsRequest;
    }

    public static EventsRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new EventsRequestJsonUnmarshaller();
        }
        return instance;
    }
}
