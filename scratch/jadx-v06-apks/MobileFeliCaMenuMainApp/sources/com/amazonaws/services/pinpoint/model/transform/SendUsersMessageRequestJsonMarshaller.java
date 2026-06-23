package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DirectMessageConfiguration;
import com.amazonaws.services.pinpoint.model.EndpointSendConfiguration;
import com.amazonaws.services.pinpoint.model.SendUsersMessageRequest;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class SendUsersMessageRequestJsonMarshaller {
    private static SendUsersMessageRequestJsonMarshaller instance;

    SendUsersMessageRequestJsonMarshaller() {
    }

    public void marshall(SendUsersMessageRequest sendUsersMessageRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (sendUsersMessageRequest.getContext() != null) {
            Map<String, String> context = sendUsersMessageRequest.getContext();
            awsJsonWriter.name("Context");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, String> entry : context.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    awsJsonWriter.value(value);
                }
            }
            awsJsonWriter.endObject();
        }
        if (sendUsersMessageRequest.getMessageConfiguration() != null) {
            DirectMessageConfiguration messageConfiguration = sendUsersMessageRequest.getMessageConfiguration();
            awsJsonWriter.name("MessageConfiguration");
            DirectMessageConfigurationJsonMarshaller.getInstance().marshall(messageConfiguration, awsJsonWriter);
        }
        if (sendUsersMessageRequest.getTraceId() != null) {
            String traceId = sendUsersMessageRequest.getTraceId();
            awsJsonWriter.name("TraceId");
            awsJsonWriter.value(traceId);
        }
        if (sendUsersMessageRequest.getUsers() != null) {
            Map<String, EndpointSendConfiguration> users = sendUsersMessageRequest.getUsers();
            awsJsonWriter.name("Users");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, EndpointSendConfiguration> entry2 : users.entrySet()) {
                EndpointSendConfiguration value2 = entry2.getValue();
                if (value2 != null) {
                    awsJsonWriter.name(entry2.getKey());
                    EndpointSendConfigurationJsonMarshaller.getInstance().marshall(value2, awsJsonWriter);
                }
            }
            awsJsonWriter.endObject();
        }
        awsJsonWriter.endObject();
    }

    public static SendUsersMessageRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SendUsersMessageRequestJsonMarshaller();
        }
        return instance;
    }
}
