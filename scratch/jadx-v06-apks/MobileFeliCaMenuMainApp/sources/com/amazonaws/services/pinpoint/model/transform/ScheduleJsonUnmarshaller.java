package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.Schedule;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class ScheduleJsonUnmarshaller implements Unmarshaller<Schedule, JsonUnmarshallerContext> {
    private static ScheduleJsonUnmarshaller instance;

    ScheduleJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public Schedule unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        Schedule schedule = new Schedule();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("EndTime")) {
                schedule.setEndTime(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("EventFilter")) {
                schedule.setEventFilter(CampaignEventFilterJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Frequency")) {
                schedule.setFrequency(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("IsLocalTime")) {
                schedule.setIsLocalTime(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("QuietTime")) {
                schedule.setQuietTime(QuietTimeJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("StartTime")) {
                schedule.setStartTime(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Timezone")) {
                schedule.setTimezone(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return schedule;
    }

    public static ScheduleJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ScheduleJsonUnmarshaller();
        }
        return instance;
    }
}
