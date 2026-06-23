package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CampaignHook;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class CampaignHookJsonMarshaller {
    private static CampaignHookJsonMarshaller instance;

    CampaignHookJsonMarshaller() {
    }

    public void marshall(CampaignHook campaignHook, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (campaignHook.getLambdaFunctionName() != null) {
            String lambdaFunctionName = campaignHook.getLambdaFunctionName();
            awsJsonWriter.name("LambdaFunctionName");
            awsJsonWriter.value(lambdaFunctionName);
        }
        if (campaignHook.getMode() != null) {
            String mode = campaignHook.getMode();
            awsJsonWriter.name("Mode");
            awsJsonWriter.value(mode);
        }
        if (campaignHook.getWebUrl() != null) {
            String webUrl = campaignHook.getWebUrl();
            awsJsonWriter.name("WebUrl");
            awsJsonWriter.value(webUrl);
        }
        awsJsonWriter.endObject();
    }

    public static CampaignHookJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new CampaignHookJsonMarshaller();
        }
        return instance;
    }
}
