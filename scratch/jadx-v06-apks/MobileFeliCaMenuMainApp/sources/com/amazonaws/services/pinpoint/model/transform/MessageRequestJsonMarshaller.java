package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.AddressConfiguration;
import com.amazonaws.services.pinpoint.model.DirectMessageConfiguration;
import com.amazonaws.services.pinpoint.model.EndpointSendConfiguration;
import com.amazonaws.services.pinpoint.model.MessageRequest;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class MessageRequestJsonMarshaller {
    private static MessageRequestJsonMarshaller instance;

    MessageRequestJsonMarshaller() {
    }

    public void marshall(MessageRequest messageRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (messageRequest.getAddresses() != null) {
            Map<String, AddressConfiguration> addresses = messageRequest.getAddresses();
            awsJsonWriter.name("Addresses");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, AddressConfiguration> entry : addresses.entrySet()) {
                AddressConfiguration value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    AddressConfigurationJsonMarshaller.getInstance().marshall(value, awsJsonWriter);
                }
            }
            awsJsonWriter.endObject();
        }
        if (messageRequest.getContext() != null) {
            Map<String, String> context = messageRequest.getContext();
            awsJsonWriter.name("Context");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, String> entry2 : context.entrySet()) {
                String value2 = entry2.getValue();
                if (value2 != null) {
                    awsJsonWriter.name(entry2.getKey());
                    awsJsonWriter.value(value2);
                }
            }
            awsJsonWriter.endObject();
        }
        if (messageRequest.getEndpoints() != null) {
            Map<String, EndpointSendConfiguration> endpoints = messageRequest.getEndpoints();
            awsJsonWriter.name("Endpoints");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, EndpointSendConfiguration> entry3 : endpoints.entrySet()) {
                EndpointSendConfiguration value3 = entry3.getValue();
                if (value3 != null) {
                    awsJsonWriter.name(entry3.getKey());
                    EndpointSendConfigurationJsonMarshaller.getInstance().marshall(value3, awsJsonWriter);
                }
            }
            awsJsonWriter.endObject();
        }
        if (messageRequest.getMessageConfiguration() != null) {
            DirectMessageConfiguration messageConfiguration = messageRequest.getMessageConfiguration();
            awsJsonWriter.name("MessageConfiguration");
            DirectMessageConfigurationJsonMarshaller.getInstance().marshall(messageConfiguration, awsJsonWriter);
        }
        if (messageRequest.getTraceId() != null) {
            String traceId = messageRequest.getTraceId();
            awsJsonWriter.name("TraceId");
            awsJsonWriter.value(traceId);
        }
        awsJsonWriter.endObject();
    }

    public static MessageRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new MessageRequestJsonMarshaller();
        }
        return instance;
    }
}
