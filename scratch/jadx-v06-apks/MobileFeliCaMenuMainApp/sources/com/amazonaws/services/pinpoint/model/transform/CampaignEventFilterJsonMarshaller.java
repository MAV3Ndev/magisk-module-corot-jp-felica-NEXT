package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CampaignEventFilter;
import com.amazonaws.services.pinpoint.model.EventDimensions;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class CampaignEventFilterJsonMarshaller {
    private static CampaignEventFilterJsonMarshaller instance;

    CampaignEventFilterJsonMarshaller() {
    }

    public void marshall(CampaignEventFilter campaignEventFilter, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (campaignEventFilter.getDimensions() != null) {
            EventDimensions dimensions = campaignEventFilter.getDimensions();
            awsJsonWriter.name("Dimensions");
            EventDimensionsJsonMarshaller.getInstance().marshall(dimensions, awsJsonWriter);
        }
        if (campaignEventFilter.getFilterType() != null) {
            String filterType = campaignEventFilter.getFilterType();
            awsJsonWriter.name("FilterType");
            awsJsonWriter.value(filterType);
        }
        awsJsonWriter.endObject();
    }

    public static CampaignEventFilterJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new CampaignEventFilterJsonMarshaller();
        }
        return instance;
    }
}
