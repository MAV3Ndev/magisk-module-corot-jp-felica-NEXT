package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetCampaignActivitiesResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignActivitiesResultJsonUnmarshaller implements Unmarshaller<GetCampaignActivitiesResult, JsonUnmarshallerContext> {
    private static GetCampaignActivitiesResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public GetCampaignActivitiesResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetCampaignActivitiesResult();
    }

    public static GetCampaignActivitiesResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetCampaignActivitiesResultJsonUnmarshaller();
        }
        return instance;
    }
}
