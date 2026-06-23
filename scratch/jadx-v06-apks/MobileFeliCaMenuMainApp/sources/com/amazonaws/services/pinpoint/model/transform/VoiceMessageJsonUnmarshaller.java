package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.VoiceMessage;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class VoiceMessageJsonUnmarshaller implements Unmarshaller<VoiceMessage, JsonUnmarshallerContext> {
    private static VoiceMessageJsonUnmarshaller instance;

    VoiceMessageJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public VoiceMessage unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        VoiceMessage voiceMessage = new VoiceMessage();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Body")) {
                voiceMessage.setBody(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("LanguageCode")) {
                voiceMessage.setLanguageCode(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("OriginationNumber")) {
                voiceMessage.setOriginationNumber(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Substitutions")) {
                voiceMessage.setSubstitutions(new MapUnmarshaller(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance())).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("VoiceId")) {
                voiceMessage.setVoiceId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return voiceMessage;
    }

    public static VoiceMessageJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new VoiceMessageJsonUnmarshaller();
        }
        return instance;
    }
}
