package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EventsResponse;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class EventsResponseJsonUnmarshaller implements Unmarshaller<EventsResponse, JsonUnmarshallerContext> {
    private static EventsResponseJsonUnmarshaller instance;

    EventsResponseJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public EventsResponse unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        EventsResponse eventsResponse = new EventsResponse();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("Results")) {
                eventsResponse.setResults(new MapUnmarshaller(ItemResponseJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return eventsResponse;
    }

    public static EventsResponseJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new EventsResponseJsonUnmarshaller();
        }
        return instance;
    }
}
