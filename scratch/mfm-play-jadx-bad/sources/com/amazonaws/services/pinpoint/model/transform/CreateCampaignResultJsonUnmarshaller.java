package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CreateCampaignResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class CreateCampaignResultJsonUnmarshaller implements Unmarshaller<CreateCampaignResult, JsonUnmarshallerContext> {
    private static CreateCampaignResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public CreateCampaignResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new CreateCampaignResult();
    }

    public static CreateCampaignResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CreateCampaignResultJsonUnmarshaller();
        }
        return instance;
    }
}
