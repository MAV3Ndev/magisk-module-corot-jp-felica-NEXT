package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ADMChannelRequest;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class ADMChannelRequestJsonMarshaller {
    private static ADMChannelRequestJsonMarshaller instance;

    ADMChannelRequestJsonMarshaller() {
    }

    public void marshall(ADMChannelRequest aDMChannelRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (aDMChannelRequest.getClientId() != null) {
            String clientId = aDMChannelRequest.getClientId();
            awsJsonWriter.name("ClientId");
            awsJsonWriter.value(clientId);
        }
        if (aDMChannelRequest.getClientSecret() != null) {
            String clientSecret = aDMChannelRequest.getClientSecret();
            awsJsonWriter.name("ClientSecret");
            awsJsonWriter.value(clientSecret);
        }
        if (aDMChannelRequest.getEnabled() != null) {
            Boolean enabled = aDMChannelRequest.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        awsJsonWriter.endObject();
    }

    public static ADMChannelRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ADMChannelRequestJsonMarshaller();
        }
        return instance;
    }
}
