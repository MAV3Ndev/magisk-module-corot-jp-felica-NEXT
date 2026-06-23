package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CampaignLimits;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class CampaignLimitsJsonMarshaller {
    private static CampaignLimitsJsonMarshaller instance;

    CampaignLimitsJsonMarshaller() {
    }

    public void marshall(CampaignLimits campaignLimits, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (campaignLimits.getDaily() != null) {
            Integer daily = campaignLimits.getDaily();
            awsJsonWriter.name("Daily");
            awsJsonWriter.value(daily);
        }
        if (campaignLimits.getMaximumDuration() != null) {
            Integer maximumDuration = campaignLimits.getMaximumDuration();
            awsJsonWriter.name("MaximumDuration");
            awsJsonWriter.value(maximumDuration);
        }
        if (campaignLimits.getMessagesPerSecond() != null) {
            Integer messagesPerSecond = campaignLimits.getMessagesPerSecond();
            awsJsonWriter.name("MessagesPerSecond");
            awsJsonWriter.value(messagesPerSecond);
        }
        if (campaignLimits.getTotal() != null) {
            Integer total = campaignLimits.getTotal();
            awsJsonWriter.name("Total");
            awsJsonWriter.value(total);
        }
        awsJsonWriter.endObject();
    }

    public static CampaignLimitsJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new CampaignLimitsJsonMarshaller();
        }
        return instance;
    }
}
