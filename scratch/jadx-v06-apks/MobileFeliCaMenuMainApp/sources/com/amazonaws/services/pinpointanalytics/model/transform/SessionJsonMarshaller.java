package com.amazonaws.services.pinpointanalytics.model.transform;

import com.amazonaws.services.pinpointanalytics.model.Session;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class SessionJsonMarshaller {
    private static SessionJsonMarshaller instance;

    SessionJsonMarshaller() {
    }

    public void marshall(Session session, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (session.getId() != null) {
            String id = session.getId();
            awsJsonWriter.name("id");
            awsJsonWriter.value(id);
        }
        if (session.getDuration() != null) {
            Long duration = session.getDuration();
            awsJsonWriter.name("duration");
            awsJsonWriter.value(duration);
        }
        if (session.getStartTimestamp() != null) {
            String startTimestamp = session.getStartTimestamp();
            awsJsonWriter.name("startTimestamp");
            awsJsonWriter.value(startTimestamp);
        }
        if (session.getStopTimestamp() != null) {
            String stopTimestamp = session.getStopTimestamp();
            awsJsonWriter.name("stopTimestamp");
            awsJsonWriter.value(stopTimestamp);
        }
        awsJsonWriter.endObject();
    }

    public static SessionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SessionJsonMarshaller();
        }
        return instance;
    }
}
