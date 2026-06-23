package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.Session;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class SessionJsonMarshaller {
    private static SessionJsonMarshaller instance;

    SessionJsonMarshaller() {
    }

    public void marshall(Session session, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (session.getDuration() != null) {
            Integer duration = session.getDuration();
            awsJsonWriter.name("Duration");
            awsJsonWriter.value(duration);
        }
        if (session.getId() != null) {
            String id = session.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (session.getStartTimestamp() != null) {
            String startTimestamp = session.getStartTimestamp();
            awsJsonWriter.name("StartTimestamp");
            awsJsonWriter.value(startTimestamp);
        }
        if (session.getStopTimestamp() != null) {
            String stopTimestamp = session.getStopTimestamp();
            awsJsonWriter.name("StopTimestamp");
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
