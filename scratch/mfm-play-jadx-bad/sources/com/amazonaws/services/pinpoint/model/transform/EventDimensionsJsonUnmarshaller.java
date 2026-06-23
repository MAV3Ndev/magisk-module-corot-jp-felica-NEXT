package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EventDimensions;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class EventDimensionsJsonUnmarshaller implements Unmarshaller<EventDimensions, JsonUnmarshallerContext> {
    private static EventDimensionsJsonUnmarshaller instance;

    EventDimensionsJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public EventDimensions unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        EventDimensions eventDimensions = new EventDimensions();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Attributes")) {
                eventDimensions.setAttributes(new MapUnmarshaller(AttributeDimensionJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("EventType")) {
                eventDimensions.setEventType(SetDimensionJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Metrics")) {
                eventDimensions.setMetrics(new MapUnmarshaller(MetricDimensionJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return eventDimensions;
    }

    public static EventDimensionsJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new EventDimensionsJsonUnmarshaller();
        }
        return instance;
    }
}
