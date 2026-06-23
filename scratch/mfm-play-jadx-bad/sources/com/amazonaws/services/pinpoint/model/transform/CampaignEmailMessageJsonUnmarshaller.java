package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CampaignEmailMessage;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class CampaignEmailMessageJsonUnmarshaller implements Unmarshaller<CampaignEmailMessage, JsonUnmarshallerContext> {
    private static CampaignEmailMessageJsonUnmarshaller instance;

    CampaignEmailMessageJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public CampaignEmailMessage unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        CampaignEmailMessage campaignEmailMessage = new CampaignEmailMessage();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Body")) {
                campaignEmailMessage.setBody(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("FromAddress")) {
                campaignEmailMessage.setFromAddress(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("HtmlBody")) {
                campaignEmailMessage.setHtmlBody(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Title")) {
                campaignEmailMessage.setTitle(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return campaignEmailMessage;
    }

    public static CampaignEmailMessageJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CampaignEmailMessageJsonUnmarshaller();
        }
        return instance;
    }
}
