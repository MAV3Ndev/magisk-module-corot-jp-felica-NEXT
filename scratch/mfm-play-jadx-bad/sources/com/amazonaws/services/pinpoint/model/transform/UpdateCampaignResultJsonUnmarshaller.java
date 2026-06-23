package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.UpdateCampaignResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class UpdateCampaignResultJsonUnmarshaller implements Unmarshaller<UpdateCampaignResult, JsonUnmarshallerContext> {
    private static UpdateCampaignResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public UpdateCampaignResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new UpdateCampaignResult();
    }

    public static UpdateCampaignResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateCampaignResultJsonUnmarshaller();
        }
        return instance;
    }
}
