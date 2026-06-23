package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EventItemResponse;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class EventItemResponseJsonMarshaller {
    private static EventItemResponseJsonMarshaller instance;

    EventItemResponseJsonMarshaller() {
    }

    public void marshall(EventItemResponse eventItemResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (eventItemResponse.getMessage() != null) {
            String message = eventItemResponse.getMessage();
            awsJsonWriter.name("Message");
            awsJsonWriter.value(message);
        }
        if (eventItemResponse.getStatusCode() != null) {
            Integer statusCode = eventItemResponse.getStatusCode();
            awsJsonWriter.name("StatusCode");
            awsJsonWriter.value(statusCode);
        }
        awsJsonWriter.endObject();
    }

    public static EventItemResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EventItemResponseJsonMarshaller();
        }
        return instance;
    }
}
