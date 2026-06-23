package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.MessageResult;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class MessageResultJsonMarshaller {
    private static MessageResultJsonMarshaller instance;

    MessageResultJsonMarshaller() {
    }

    public void marshall(MessageResult messageResult, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (messageResult.getDeliveryStatus() != null) {
            String deliveryStatus = messageResult.getDeliveryStatus();
            awsJsonWriter.name("DeliveryStatus");
            awsJsonWriter.value(deliveryStatus);
        }
        if (messageResult.getMessageId() != null) {
            String messageId = messageResult.getMessageId();
            awsJsonWriter.name("MessageId");
            awsJsonWriter.value(messageId);
        }
        if (messageResult.getStatusCode() != null) {
            Integer statusCode = messageResult.getStatusCode();
            awsJsonWriter.name("StatusCode");
            awsJsonWriter.value(statusCode);
        }
        if (messageResult.getStatusMessage() != null) {
            String statusMessage = messageResult.getStatusMessage();
            awsJsonWriter.name("StatusMessage");
            awsJsonWriter.value(statusMessage);
        }
        if (messageResult.getUpdatedToken() != null) {
            String updatedToken = messageResult.getUpdatedToken();
            awsJsonWriter.name("UpdatedToken");
            awsJsonWriter.value(updatedToken);
        }
        awsJsonWriter.endObject();
    }

    public static MessageResultJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new MessageResultJsonMarshaller();
        }
        return instance;
    }
}
