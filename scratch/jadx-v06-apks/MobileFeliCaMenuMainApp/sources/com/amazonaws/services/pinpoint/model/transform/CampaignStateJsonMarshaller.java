package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CampaignState;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class CampaignStateJsonMarshaller {
    private static CampaignStateJsonMarshaller instance;

    CampaignStateJsonMarshaller() {
    }

    public void marshall(CampaignState campaignState, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (campaignState.getCampaignStatus() != null) {
            String campaignStatus = campaignState.getCampaignStatus();
            awsJsonWriter.name("CampaignStatus");
            awsJsonWriter.value(campaignStatus);
        }
        awsJsonWriter.endObject();
    }

    public static CampaignStateJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new CampaignStateJsonMarshaller();
        }
        return instance;
    }
}
