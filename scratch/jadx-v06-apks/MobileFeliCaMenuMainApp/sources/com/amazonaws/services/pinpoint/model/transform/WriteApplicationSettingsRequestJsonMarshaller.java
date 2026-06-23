package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CampaignHook;
import com.amazonaws.services.pinpoint.model.CampaignLimits;
import com.amazonaws.services.pinpoint.model.QuietTime;
import com.amazonaws.services.pinpoint.model.WriteApplicationSettingsRequest;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class WriteApplicationSettingsRequestJsonMarshaller {
    private static WriteApplicationSettingsRequestJsonMarshaller instance;

    WriteApplicationSettingsRequestJsonMarshaller() {
    }

    public void marshall(WriteApplicationSettingsRequest writeApplicationSettingsRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (writeApplicationSettingsRequest.getCampaignHook() != null) {
            CampaignHook campaignHook = writeApplicationSettingsRequest.getCampaignHook();
            awsJsonWriter.name("CampaignHook");
            CampaignHookJsonMarshaller.getInstance().marshall(campaignHook, awsJsonWriter);
        }
        if (writeApplicationSettingsRequest.getCloudWatchMetricsEnabled() != null) {
            Boolean cloudWatchMetricsEnabled = writeApplicationSettingsRequest.getCloudWatchMetricsEnabled();
            awsJsonWriter.name("CloudWatchMetricsEnabled");
            awsJsonWriter.value(cloudWatchMetricsEnabled.booleanValue());
        }
        if (writeApplicationSettingsRequest.getLimits() != null) {
            CampaignLimits limits = writeApplicationSettingsRequest.getLimits();
            awsJsonWriter.name("Limits");
            CampaignLimitsJsonMarshaller.getInstance().marshall(limits, awsJsonWriter);
        }
        if (writeApplicationSettingsRequest.getQuietTime() != null) {
            QuietTime quietTime = writeApplicationSettingsRequest.getQuietTime();
            awsJsonWriter.name("QuietTime");
            QuietTimeJsonMarshaller.getInstance().marshall(quietTime, awsJsonWriter);
        }
        awsJsonWriter.endObject();
    }

    public static WriteApplicationSettingsRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new WriteApplicationSettingsRequestJsonMarshaller();
        }
        return instance;
    }
}
