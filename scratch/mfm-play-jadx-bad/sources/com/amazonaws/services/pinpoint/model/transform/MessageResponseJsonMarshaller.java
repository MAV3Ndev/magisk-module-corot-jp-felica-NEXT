package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EndpointMessageResult;
import com.amazonaws.services.pinpoint.model.MessageResponse;
import com.amazonaws.services.pinpoint.model.MessageResult;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class MessageResponseJsonMarshaller {
    private static MessageResponseJsonMarshaller instance;

    MessageResponseJsonMarshaller() {
    }

    public void marshall(MessageResponse messageResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (messageResponse.getApplicationId() != null) {
            String applicationId = messageResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (messageResponse.getEndpointResult() != null) {
            Map<String, EndpointMessageResult> endpointResult = messageResponse.getEndpointResult();
            awsJsonWriter.name("EndpointResult");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, EndpointMessageResult> entry : endpointResult.entrySet()) {
                EndpointMessageResult value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    EndpointMessageResultJsonMarshaller.getInstance().marshall(value, awsJsonWriter);
                }
            }
            awsJsonWriter.endObject();
        }
        if (messageResponse.getRequestId() != null) {
            String requestId = messageResponse.getRequestId();
            awsJsonWriter.name("RequestId");
            awsJsonWriter.value(requestId);
        }
        if (messageResponse.getResult() != null) {
            Map<String, MessageResult> result = messageResponse.getResult();
            awsJsonWriter.name("Result");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, MessageResult> entry2 : result.entrySet()) {
                MessageResult value2 = entry2.getValue();
                if (value2 != null) {
                    awsJsonWriter.name(entry2.getKey());
                    MessageResultJsonMarshaller.getInstance().marshall(value2, awsJsonWriter);
                }
            }
            awsJsonWriter.endObject();
        }
        awsJsonWriter.endObject();
    }

    public static MessageResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new MessageResponseJsonMarshaller();
        }
        return instance;
    }
}
