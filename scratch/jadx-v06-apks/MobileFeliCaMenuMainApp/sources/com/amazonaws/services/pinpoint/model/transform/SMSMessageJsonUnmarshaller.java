package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SMSMessage;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class SMSMessageJsonUnmarshaller implements Unmarshaller<SMSMessage, JsonUnmarshallerContext> {
    private static SMSMessageJsonUnmarshaller instance;

    SMSMessageJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public SMSMessage unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SMSMessage sMSMessage = new SMSMessage();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Body")) {
                sMSMessage.setBody(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Keyword")) {
                sMSMessage.setKeyword(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("MessageType")) {
                sMSMessage.setMessageType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("OriginationNumber")) {
                sMSMessage.setOriginationNumber(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("SenderId")) {
                sMSMessage.setSenderId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Substitutions")) {
                sMSMessage.setSubstitutions(new MapUnmarshaller(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance())).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return sMSMessage;
    }

    public static SMSMessageJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SMSMessageJsonUnmarshaller();
        }
        return instance;
    }
}
