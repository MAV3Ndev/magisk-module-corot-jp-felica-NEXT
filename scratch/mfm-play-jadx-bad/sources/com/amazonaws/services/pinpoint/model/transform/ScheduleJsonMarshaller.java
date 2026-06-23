package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CampaignEventFilter;
import com.amazonaws.services.pinpoint.model.QuietTime;
import com.amazonaws.services.pinpoint.model.Schedule;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class ScheduleJsonMarshaller {
    private static ScheduleJsonMarshaller instance;

    ScheduleJsonMarshaller() {
    }

    public void marshall(Schedule schedule, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (schedule.getEndTime() != null) {
            String endTime = schedule.getEndTime();
            awsJsonWriter.name("EndTime");
            awsJsonWriter.value(endTime);
        }
        if (schedule.getEventFilter() != null) {
            CampaignEventFilter eventFilter = schedule.getEventFilter();
            awsJsonWriter.name("EventFilter");
            CampaignEventFilterJsonMarshaller.getInstance().marshall(eventFilter, awsJsonWriter);
        }
        if (schedule.getFrequency() != null) {
            String frequency = schedule.getFrequency();
            awsJsonWriter.name("Frequency");
            awsJsonWriter.value(frequency);
        }
        if (schedule.getIsLocalTime() != null) {
            Boolean isLocalTime = schedule.getIsLocalTime();
            awsJsonWriter.name("IsLocalTime");
            awsJsonWriter.value(isLocalTime.booleanValue());
        }
        if (schedule.getQuietTime() != null) {
            QuietTime quietTime = schedule.getQuietTime();
            awsJsonWriter.name("QuietTime");
            QuietTimeJsonMarshaller.getInstance().marshall(quietTime, awsJsonWriter);
        }
        if (schedule.getStartTime() != null) {
            String startTime = schedule.getStartTime();
            awsJsonWriter.name("StartTime");
            awsJsonWriter.value(startTime);
        }
        if (schedule.getTimezone() != null) {
            String timezone = schedule.getTimezone();
            awsJsonWriter.name("Timezone");
            awsJsonWriter.value(timezone);
        }
        awsJsonWriter.endObject();
    }

    public static ScheduleJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ScheduleJsonMarshaller();
        }
        return instance;
    }
}
