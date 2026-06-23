package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.QuietTime;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class QuietTimeJsonMarshaller {
    private static QuietTimeJsonMarshaller instance;

    QuietTimeJsonMarshaller() {
    }

    public void marshall(QuietTime quietTime, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (quietTime.getEnd() != null) {
            String end = quietTime.getEnd();
            awsJsonWriter.name("End");
            awsJsonWriter.value(end);
        }
        if (quietTime.getStart() != null) {
            String start = quietTime.getStart();
            awsJsonWriter.name("Start");
            awsJsonWriter.value(start);
        }
        awsJsonWriter.endObject();
    }

    public static QuietTimeJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new QuietTimeJsonMarshaller();
        }
        return instance;
    }
}
