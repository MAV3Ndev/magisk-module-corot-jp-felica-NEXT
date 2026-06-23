package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GCMChannelRequest;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class GCMChannelRequestJsonMarshaller {
    private static GCMChannelRequestJsonMarshaller instance;

    GCMChannelRequestJsonMarshaller() {
    }

    public void marshall(GCMChannelRequest gCMChannelRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (gCMChannelRequest.getApiKey() != null) {
            String apiKey = gCMChannelRequest.getApiKey();
            awsJsonWriter.name("ApiKey");
            awsJsonWriter.value(apiKey);
        }
        if (gCMChannelRequest.getEnabled() != null) {
            Boolean enabled = gCMChannelRequest.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        awsJsonWriter.endObject();
    }

    public static GCMChannelRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new GCMChannelRequestJsonMarshaller();
        }
        return instance;
    }
}
