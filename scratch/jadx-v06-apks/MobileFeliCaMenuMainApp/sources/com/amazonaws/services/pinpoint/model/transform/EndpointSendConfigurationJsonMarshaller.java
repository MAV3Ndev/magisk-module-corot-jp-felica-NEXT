package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EndpointSendConfiguration;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class EndpointSendConfigurationJsonMarshaller {
    private static EndpointSendConfigurationJsonMarshaller instance;

    EndpointSendConfigurationJsonMarshaller() {
    }

    public void marshall(EndpointSendConfiguration endpointSendConfiguration, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (endpointSendConfiguration.getBodyOverride() != null) {
            String bodyOverride = endpointSendConfiguration.getBodyOverride();
            awsJsonWriter.name("BodyOverride");
            awsJsonWriter.value(bodyOverride);
        }
        if (endpointSendConfiguration.getContext() != null) {
            Map<String, String> context = endpointSendConfiguration.getContext();
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
        if (endpointSendConfiguration.getRawContent() != null) {
            String rawContent = endpointSendConfiguration.getRawContent();
            awsJsonWriter.name("RawContent");
            awsJsonWriter.value(rawContent);
        }
        if (endpointSendConfiguration.getSubstitutions() != null) {
            Map<String, List<String>> substitutions = endpointSendConfiguration.getSubstitutions();
            awsJsonWriter.name("Substitutions");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, List<String>> entry2 : substitutions.entrySet()) {
                List<String> value2 = entry2.getValue();
                if (value2 != null) {
                    awsJsonWriter.name(entry2.getKey());
                    awsJsonWriter.beginArray();
                    for (String str : value2) {
                        if (str != null) {
                            awsJsonWriter.value(str);
                        }
                    }
                    awsJsonWriter.endArray();
                }
            }
            awsJsonWriter.endObject();
        }
        if (endpointSendConfiguration.getTitleOverride() != null) {
            String titleOverride = endpointSendConfiguration.getTitleOverride();
            awsJsonWriter.name("TitleOverride");
            awsJsonWriter.value(titleOverride);
        }
        awsJsonWriter.endObject();
    }

    public static EndpointSendConfigurationJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EndpointSendConfigurationJsonMarshaller();
        }
        return instance;
    }
}
