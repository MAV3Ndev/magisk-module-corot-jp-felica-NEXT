package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CampaignLimits;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class CampaignLimitsJsonUnmarshaller implements Unmarshaller<CampaignLimits, JsonUnmarshallerContext> {
    private static CampaignLimitsJsonUnmarshaller instance;

    CampaignLimitsJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public CampaignLimits unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        CampaignLimits campaignLimits = new CampaignLimits();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Daily")) {
                campaignLimits.setDaily(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("MaximumDuration")) {
                campaignLimits.setMaximumDuration(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("MessagesPerSecond")) {
                campaignLimits.setMessagesPerSecond(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Total")) {
                campaignLimits.setTotal(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return campaignLimits;
    }

    public static CampaignLimitsJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CampaignLimitsJsonUnmarshaller();
        }
        return instance;
    }
}
