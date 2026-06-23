package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EmailChannelRequest;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class EmailChannelRequestJsonMarshaller {
    private static EmailChannelRequestJsonMarshaller instance;

    EmailChannelRequestJsonMarshaller() {
    }

    public void marshall(EmailChannelRequest emailChannelRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (emailChannelRequest.getConfigurationSet() != null) {
            String configurationSet = emailChannelRequest.getConfigurationSet();
            awsJsonWriter.name("ConfigurationSet");
            awsJsonWriter.value(configurationSet);
        }
        if (emailChannelRequest.getEnabled() != null) {
            Boolean enabled = emailChannelRequest.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (emailChannelRequest.getFromAddress() != null) {
            String fromAddress = emailChannelRequest.getFromAddress();
            awsJsonWriter.name("FromAddress");
            awsJsonWriter.value(fromAddress);
        }
        if (emailChannelRequest.getIdentity() != null) {
            String identity = emailChannelRequest.getIdentity();
            awsJsonWriter.name("Identity");
            awsJsonWriter.value(identity);
        }
        if (emailChannelRequest.getRoleArn() != null) {
            String roleArn = emailChannelRequest.getRoleArn();
            awsJsonWriter.name("RoleArn");
            awsJsonWriter.value(roleArn);
        }
        awsJsonWriter.endObject();
    }

    public static EmailChannelRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EmailChannelRequestJsonMarshaller();
        }
        return instance;
    }
}
