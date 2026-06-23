package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.VoiceChannelRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class VoiceChannelRequestJsonUnmarshaller implements Unmarshaller<VoiceChannelRequest, JsonUnmarshallerContext> {
    private static VoiceChannelRequestJsonUnmarshaller instance;

    VoiceChannelRequestJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public VoiceChannelRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        VoiceChannelRequest voiceChannelRequest = new VoiceChannelRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("Enabled")) {
                voiceChannelRequest.setEnabled(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return voiceChannelRequest;
    }

    public static VoiceChannelRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new VoiceChannelRequestJsonUnmarshaller();
        }
        return instance;
    }
}
