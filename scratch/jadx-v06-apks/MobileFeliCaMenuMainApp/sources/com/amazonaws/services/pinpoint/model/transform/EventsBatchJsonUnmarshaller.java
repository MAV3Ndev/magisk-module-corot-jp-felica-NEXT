package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EventsBatch;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class EventsBatchJsonUnmarshaller implements Unmarshaller<EventsBatch, JsonUnmarshallerContext> {
    private static EventsBatchJsonUnmarshaller instance;

    EventsBatchJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public EventsBatch unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        EventsBatch eventsBatch = new EventsBatch();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Endpoint")) {
                eventsBatch.setEndpoint(PublicEndpointJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Events")) {
                eventsBatch.setEvents(new MapUnmarshaller(EventJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return eventsBatch;
    }

    public static EventsBatchJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new EventsBatchJsonUnmarshaller();
        }
        return instance;
    }
}
