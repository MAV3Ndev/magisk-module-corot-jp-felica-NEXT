package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetCampaignVersionsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetCampaignVersionsResultJsonUnmarshaller implements Unmarshaller<GetCampaignVersionsResult, JsonUnmarshallerContext> {
    private static GetCampaignVersionsResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public GetCampaignVersionsResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetCampaignVersionsResult();
    }

    public static GetCampaignVersionsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetCampaignVersionsResultJsonUnmarshaller();
        }
        return instance;
    }
}
