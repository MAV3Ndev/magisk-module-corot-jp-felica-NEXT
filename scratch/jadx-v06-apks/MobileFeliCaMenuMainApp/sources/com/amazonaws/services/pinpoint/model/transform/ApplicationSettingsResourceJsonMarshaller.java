package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ApplicationSettingsResource;
import com.amazonaws.services.pinpoint.model.CampaignHook;
import com.amazonaws.services.pinpoint.model.CampaignLimits;
import com.amazonaws.services.pinpoint.model.QuietTime;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class ApplicationSettingsResourceJsonMarshaller {
    private static ApplicationSettingsResourceJsonMarshaller instance;

    ApplicationSettingsResourceJsonMarshaller() {
    }

    public void marshall(ApplicationSettingsResource applicationSettingsResource, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (applicationSettingsResource.getApplicationId() != null) {
            String applicationId = applicationSettingsResource.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (applicationSettingsResource.getCampaignHook() != null) {
            CampaignHook campaignHook = applicationSettingsResource.getCampaignHook();
            awsJsonWriter.name("CampaignHook");
            CampaignHookJsonMarshaller.getInstance().marshall(campaignHook, awsJsonWriter);
        }
        if (applicationSettingsResource.getLastModifiedDate() != null) {
            String lastModifiedDate = applicationSettingsResource.getLastModifiedDate();
            awsJsonWriter.name("LastModifiedDate");
            awsJsonWriter.value(lastModifiedDate);
        }
        if (applicationSettingsResource.getLimits() != null) {
            CampaignLimits limits = applicationSettingsResource.getLimits();
            awsJsonWriter.name("Limits");
            CampaignLimitsJsonMarshaller.getInstance().marshall(limits, awsJsonWriter);
        }
        if (applicationSettingsResource.getQuietTime() != null) {
            QuietTime quietTime = applicationSettingsResource.getQuietTime();
            awsJsonWriter.name("QuietTime");
            QuietTimeJsonMarshaller.getInstance().marshall(quietTime, awsJsonWriter);
        }
        awsJsonWriter.endObject();
    }

    public static ApplicationSettingsResourceJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ApplicationSettingsResourceJsonMarshaller();
        }
        return instance;
    }
}
