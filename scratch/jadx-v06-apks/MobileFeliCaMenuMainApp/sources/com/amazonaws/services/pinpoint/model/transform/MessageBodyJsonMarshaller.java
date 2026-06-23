package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.MessageBody;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class MessageBodyJsonMarshaller {
    private static MessageBodyJsonMarshaller instance;

    MessageBodyJsonMarshaller() {
    }

    public void marshall(MessageBody messageBody, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (messageBody.getMessage() != null) {
            String message = messageBody.getMessage();
            awsJsonWriter.name("Message");
            awsJsonWriter.value(message);
        }
        if (messageBody.getRequestID() != null) {
            String requestID = messageBody.getRequestID();
            awsJsonWriter.name("RequestID");
            awsJsonWriter.value(requestID);
        }
        awsJsonWriter.endObject();
    }

    public static MessageBodyJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new MessageBodyJsonMarshaller();
        }
        return instance;
    }
}
