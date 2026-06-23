package com.amazonaws.services.pinpointanalytics.model.transform;

import com.amazonaws.services.pinpointanalytics.model.Event;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;
import com.felicanetworks.semc.sws.NotifyClientEventWorker;

/* JADX INFO: loaded from: classes.dex */
class EventJsonUnmarshaller implements Unmarshaller<Event, JsonUnmarshallerContext> {
    private static EventJsonUnmarshaller instance;

    EventJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public Event unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        Event event = new Event();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals(NotifyClientEventWorker.EXT_KEY_EVENT_TYPE)) {
                event.setEventType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("timestamp")) {
                event.setTimestamp(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("session")) {
                event.setSession(SessionJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("version")) {
                event.setVersion(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("attributes")) {
                event.setAttributes(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("metrics")) {
                event.setMetrics(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.DoubleJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return event;
    }

    public static EventJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new EventJsonUnmarshaller();
        }
        return instance;
    }
}
