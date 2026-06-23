package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.VoiceMessage;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class VoiceMessageJsonMarshaller {
    private static VoiceMessageJsonMarshaller instance;

    VoiceMessageJsonMarshaller() {
    }

    public void marshall(VoiceMessage voiceMessage, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (voiceMessage.getBody() != null) {
            String body = voiceMessage.getBody();
            awsJsonWriter.name("Body");
            awsJsonWriter.value(body);
        }
        if (voiceMessage.getLanguageCode() != null) {
            String languageCode = voiceMessage.getLanguageCode();
            awsJsonWriter.name("LanguageCode");
            awsJsonWriter.value(languageCode);
        }
        if (voiceMessage.getOriginationNumber() != null) {
            String originationNumber = voiceMessage.getOriginationNumber();
            awsJsonWriter.name("OriginationNumber");
            awsJsonWriter.value(originationNumber);
        }
        if (voiceMessage.getSubstitutions() != null) {
            Map<String, List<String>> substitutions = voiceMessage.getSubstitutions();
            awsJsonWriter.name("Substitutions");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, List<String>> entry : substitutions.entrySet()) {
                List<String> value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    awsJsonWriter.beginArray();
                    for (String str : value) {
                        if (str != null) {
                            awsJsonWriter.value(str);
                        }
                    }
                    awsJsonWriter.endArray();
                }
            }
            awsJsonWriter.endObject();
        }
        if (voiceMessage.getVoiceId() != null) {
            String voiceId = voiceMessage.getVoiceId();
            awsJsonWriter.name("VoiceId");
            awsJsonWriter.value(voiceId);
        }
        awsJsonWriter.endObject();
    }

    public static VoiceMessageJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new VoiceMessageJsonMarshaller();
        }
        return instance;
    }
}
