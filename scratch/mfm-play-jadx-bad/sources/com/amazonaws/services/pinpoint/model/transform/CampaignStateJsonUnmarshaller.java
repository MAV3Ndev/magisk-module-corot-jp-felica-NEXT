package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CampaignState;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class CampaignStateJsonUnmarshaller implements Unmarshaller<CampaignState, JsonUnmarshallerContext> {
    private static CampaignStateJsonUnmarshaller instance;

    CampaignStateJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public CampaignState unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        CampaignState campaignState = new CampaignState();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("CampaignStatus")) {
                campaignState.setCampaignStatus(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return campaignState;
    }

    public static CampaignStateJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CampaignStateJsonUnmarshaller();
        }
        return instance;
    }
}
