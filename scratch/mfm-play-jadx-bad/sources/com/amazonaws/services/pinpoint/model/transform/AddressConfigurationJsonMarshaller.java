package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.AddressConfiguration;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class AddressConfigurationJsonMarshaller {
    private static AddressConfigurationJsonMarshaller instance;

    AddressConfigurationJsonMarshaller() {
    }

    public void marshall(AddressConfiguration addressConfiguration, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (addressConfiguration.getBodyOverride() != null) {
            String bodyOverride = addressConfiguration.getBodyOverride();
            awsJsonWriter.name("BodyOverride");
            awsJsonWriter.value(bodyOverride);
        }
        if (addressConfiguration.getChannelType() != null) {
            String channelType = addressConfiguration.getChannelType();
            awsJsonWriter.name("ChannelType");
            awsJsonWriter.value(channelType);
        }
        if (addressConfiguration.getContext() != null) {
            Map<String, String> context = addressConfiguration.getContext();
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
        if (addressConfiguration.getRawContent() != null) {
            String rawContent = addressConfiguration.getRawContent();
            awsJsonWriter.name("RawContent");
            awsJsonWriter.value(rawContent);
        }
        if (addressConfiguration.getSubstitutions() != null) {
            Map<String, List<String>> substitutions = addressConfiguration.getSubstitutions();
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
        if (addressConfiguration.getTitleOverride() != null) {
            String titleOverride = addressConfiguration.getTitleOverride();
            awsJsonWriter.name("TitleOverride");
            awsJsonWriter.value(titleOverride);
        }
        awsJsonWriter.endObject();
    }

    public static AddressConfigurationJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new AddressConfigurationJsonMarshaller();
        }
        return instance;
    }
}
