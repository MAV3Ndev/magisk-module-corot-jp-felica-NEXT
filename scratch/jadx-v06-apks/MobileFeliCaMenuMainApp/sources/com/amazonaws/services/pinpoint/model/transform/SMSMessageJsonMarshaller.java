package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SMSMessage;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class SMSMessageJsonMarshaller {
    private static SMSMessageJsonMarshaller instance;

    SMSMessageJsonMarshaller() {
    }

    public void marshall(SMSMessage sMSMessage, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (sMSMessage.getBody() != null) {
            String body = sMSMessage.getBody();
            awsJsonWriter.name("Body");
            awsJsonWriter.value(body);
        }
        if (sMSMessage.getKeyword() != null) {
            String keyword = sMSMessage.getKeyword();
            awsJsonWriter.name("Keyword");
            awsJsonWriter.value(keyword);
        }
        if (sMSMessage.getMessageType() != null) {
            String messageType = sMSMessage.getMessageType();
            awsJsonWriter.name("MessageType");
            awsJsonWriter.value(messageType);
        }
        if (sMSMessage.getOriginationNumber() != null) {
            String originationNumber = sMSMessage.getOriginationNumber();
            awsJsonWriter.name("OriginationNumber");
            awsJsonWriter.value(originationNumber);
        }
        if (sMSMessage.getSenderId() != null) {
            String senderId = sMSMessage.getSenderId();
            awsJsonWriter.name("SenderId");
            awsJsonWriter.value(senderId);
        }
        if (sMSMessage.getSubstitutions() != null) {
            Map<String, List<String>> substitutions = sMSMessage.getSubstitutions();
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
        awsJsonWriter.endObject();
    }

    public static SMSMessageJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SMSMessageJsonMarshaller();
        }
        return instance;
    }
}
