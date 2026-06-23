package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CampaignResponse;
import com.amazonaws.services.pinpoint.model.CampaignsResponse;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class CampaignsResponseJsonMarshaller {
    private static CampaignsResponseJsonMarshaller instance;

    CampaignsResponseJsonMarshaller() {
    }

    public void marshall(CampaignsResponse campaignsResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (campaignsResponse.getItem() != null) {
            List<CampaignResponse> item = campaignsResponse.getItem();
            awsJsonWriter.name("Item");
            awsJsonWriter.beginArray();
            for (CampaignResponse campaignResponse : item) {
                if (campaignResponse != null) {
                    CampaignResponseJsonMarshaller.getInstance().marshall(campaignResponse, awsJsonWriter);
                }
            }
            awsJsonWriter.endArray();
        }
        if (campaignsResponse.getNextToken() != null) {
            String nextToken = campaignsResponse.getNextToken();
            awsJsonWriter.name("NextToken");
            awsJsonWriter.value(nextToken);
        }
        awsJsonWriter.endObject();
    }

    public static CampaignsResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new CampaignsResponseJsonMarshaller();
        }
        return instance;
    }
}
