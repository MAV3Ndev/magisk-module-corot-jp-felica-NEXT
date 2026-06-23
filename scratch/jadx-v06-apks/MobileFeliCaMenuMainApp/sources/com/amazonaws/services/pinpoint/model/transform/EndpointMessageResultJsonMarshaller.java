package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EndpointMessageResult;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class EndpointMessageResultJsonMarshaller {
    private static EndpointMessageResultJsonMarshaller instance;

    EndpointMessageResultJsonMarshaller() {
    }

    public void marshall(EndpointMessageResult endpointMessageResult, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (endpointMessageResult.getAddress() != null) {
            String address = endpointMessageResult.getAddress();
            awsJsonWriter.name("Address");
            awsJsonWriter.value(address);
        }
        if (endpointMessageResult.getDeliveryStatus() != null) {
            String deliveryStatus = endpointMessageResult.getDeliveryStatus();
            awsJsonWriter.name("DeliveryStatus");
            awsJsonWriter.value(deliveryStatus);
        }
        if (endpointMessageResult.getMessageId() != null) {
            String messageId = endpointMessageResult.getMessageId();
            awsJsonWriter.name("MessageId");
            awsJsonWriter.value(messageId);
        }
        if (endpointMessageResult.getStatusCode() != null) {
            Integer statusCode = endpointMessageResult.getStatusCode();
            awsJsonWriter.name("StatusCode");
            awsJsonWriter.value(statusCode);
        }
        if (endpointMessageResult.getStatusMessage() != null) {
            String statusMessage = endpointMessageResult.getStatusMessage();
            awsJsonWriter.name("StatusMessage");
            awsJsonWriter.value(statusMessage);
        }
        if (endpointMessageResult.getUpdatedToken() != null) {
            String updatedToken = endpointMessageResult.getUpdatedToken();
            awsJsonWriter.name("UpdatedToken");
            awsJsonWriter.value(updatedToken);
        }
        awsJsonWriter.endObject();
    }

    public static EndpointMessageResultJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EndpointMessageResultJsonMarshaller();
        }
        return instance;
    }
}
