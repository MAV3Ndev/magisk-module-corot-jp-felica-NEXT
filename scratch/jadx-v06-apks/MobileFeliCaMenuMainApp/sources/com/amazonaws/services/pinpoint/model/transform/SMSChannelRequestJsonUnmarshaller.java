package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SMSChannelRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class SMSChannelRequestJsonUnmarshaller implements Unmarshaller<SMSChannelRequest, JsonUnmarshallerContext> {
    private static SMSChannelRequestJsonUnmarshaller instance;

    SMSChannelRequestJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public SMSChannelRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SMSChannelRequest sMSChannelRequest = new SMSChannelRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Enabled")) {
                sMSChannelRequest.setEnabled(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("SenderId")) {
                sMSChannelRequest.setSenderId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("ShortCode")) {
                sMSChannelRequest.setShortCode(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return sMSChannelRequest;
    }

    public static SMSChannelRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SMSChannelRequestJsonUnmarshaller();
        }
        return instance;
    }
}
