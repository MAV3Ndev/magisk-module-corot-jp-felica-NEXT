package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EndpointMessageResult;
import com.amazonaws.services.pinpoint.model.SendUsersMessageResponse;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class SendUsersMessageResponseJsonMarshaller {
    private static SendUsersMessageResponseJsonMarshaller instance;

    SendUsersMessageResponseJsonMarshaller() {
    }

    public void marshall(SendUsersMessageResponse sendUsersMessageResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (sendUsersMessageResponse.getApplicationId() != null) {
            String applicationId = sendUsersMessageResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (sendUsersMessageResponse.getRequestId() != null) {
            String requestId = sendUsersMessageResponse.getRequestId();
            awsJsonWriter.name("RequestId");
            awsJsonWriter.value(requestId);
        }
        if (sendUsersMessageResponse.getResult() != null) {
            Map<String, Map<String, EndpointMessageResult>> result = sendUsersMessageResponse.getResult();
            awsJsonWriter.name("Result");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, Map<String, EndpointMessageResult>> entry : result.entrySet()) {
                Map<String, EndpointMessageResult> value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    awsJsonWriter.beginObject();
                    for (Map.Entry<String, EndpointMessageResult> entry2 : value.entrySet()) {
                        EndpointMessageResult value2 = entry2.getValue();
                        if (value2 != null) {
                            awsJsonWriter.name(entry2.getKey());
                            EndpointMessageResultJsonMarshaller.getInstance().marshall(value2, awsJsonWriter);
                        }
                    }
                    awsJsonWriter.endObject();
                }
            }
            awsJsonWriter.endObject();
        }
        awsJsonWriter.endObject();
    }

    public static SendUsersMessageResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SendUsersMessageResponseJsonMarshaller();
        }
        return instance;
    }
}
