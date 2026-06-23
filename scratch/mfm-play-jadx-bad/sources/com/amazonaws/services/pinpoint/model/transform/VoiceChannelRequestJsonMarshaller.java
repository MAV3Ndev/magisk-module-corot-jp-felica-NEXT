package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.VoiceChannelRequest;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class VoiceChannelRequestJsonMarshaller {
    private static VoiceChannelRequestJsonMarshaller instance;

    VoiceChannelRequestJsonMarshaller() {
    }

    public void marshall(VoiceChannelRequest voiceChannelRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (voiceChannelRequest.getEnabled() != null) {
            Boolean enabled = voiceChannelRequest.getEnabled();
            awsJsonWriter.name("Enabled");
            awsJsonWriter.value(enabled.booleanValue());
        }
        awsJsonWriter.endObject();
    }

    public static VoiceChannelRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new VoiceChannelRequestJsonMarshaller();
        }
        return instance;
    }
}
