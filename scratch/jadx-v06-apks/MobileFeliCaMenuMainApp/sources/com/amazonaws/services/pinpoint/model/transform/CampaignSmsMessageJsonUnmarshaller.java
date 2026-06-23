package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CampaignSmsMessage;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class CampaignSmsMessageJsonUnmarshaller implements Unmarshaller<CampaignSmsMessage, JsonUnmarshallerContext> {
    private static CampaignSmsMessageJsonUnmarshaller instance;

    CampaignSmsMessageJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public CampaignSmsMessage unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        CampaignSmsMessage campaignSmsMessage = new CampaignSmsMessage();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Body")) {
                campaignSmsMessage.setBody(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("MessageType")) {
                campaignSmsMessage.setMessageType(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("SenderId")) {
                campaignSmsMessage.setSenderId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return campaignSmsMessage;
    }

    public static CampaignSmsMessageJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CampaignSmsMessageJsonUnmarshaller();
        }
        return instance;
    }
}
