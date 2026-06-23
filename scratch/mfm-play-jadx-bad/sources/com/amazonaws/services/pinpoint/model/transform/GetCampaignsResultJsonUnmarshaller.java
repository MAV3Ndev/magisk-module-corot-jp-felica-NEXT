package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetCampaignsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignsResultJsonUnmarshaller implements Unmarshaller<GetCampaignsResult, JsonUnmarshallerContext> {
    private static GetCampaignsResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetCampaignsResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetCampaignsResult();
    }

    public static GetCampaignsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetCampaignsResultJsonUnmarshaller();
        }
        return instance;
    }
}
