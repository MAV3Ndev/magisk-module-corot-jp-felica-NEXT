package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.BaiduChannelRequest;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class BaiduChannelRequestJsonMarshaller {
    private static BaiduChannelRequestJsonMarshaller instance;

    BaiduChannelRequestJsonMarshaller() {
    }

    public void marshall(BaiduChannelRequest baiduChannelRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (baiduChannelRequest.getApiKey() != null) {
            String apiKey = baiduChannelRequest.getApiKey();
            awsJsonWriter.name("ApiKey");
            awsJsonWriter.value(apiKey);
        }
        if (baiduChannelRequest.getEnabled() != null) {
            Boolean enabled = baiduChannelRequest.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        if (baiduChannelRequest.getSecretKey() != null) {
            String secretKey = baiduChannelRequest.getSecretKey();
            awsJsonWriter.name("SecretKey");
            awsJsonWriter.value(secretKey);
        }
        awsJsonWriter.endObject();
    }

    public static BaiduChannelRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new BaiduChannelRequestJsonMarshaller();
        }
        return instance;
    }
}
