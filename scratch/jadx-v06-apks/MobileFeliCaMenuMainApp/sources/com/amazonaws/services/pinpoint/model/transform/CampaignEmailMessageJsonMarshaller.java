package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CampaignEmailMessage;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class CampaignEmailMessageJsonMarshaller {
    private static CampaignEmailMessageJsonMarshaller instance;

    CampaignEmailMessageJsonMarshaller() {
    }

    public void marshall(CampaignEmailMessage campaignEmailMessage, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (campaignEmailMessage.getBody() != null) {
            String body = campaignEmailMessage.getBody();
            awsJsonWriter.name("Body");
            awsJsonWriter.value(body);
        }
        if (campaignEmailMessage.getFromAddress() != null) {
            String fromAddress = campaignEmailMessage.getFromAddress();
            awsJsonWriter.name("FromAddress");
            awsJsonWriter.value(fromAddress);
        }
        if (campaignEmailMessage.getHtmlBody() != null) {
            String htmlBody = campaignEmailMessage.getHtmlBody();
            awsJsonWriter.name("HtmlBody");
            awsJsonWriter.value(htmlBody);
        }
        if (campaignEmailMessage.getTitle() != null) {
            String title = campaignEmailMessage.getTitle();
            awsJsonWriter.name("Title");
            awsJsonWriter.value(title);
        }
        awsJsonWriter.endObject();
    }

    public static CampaignEmailMessageJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new CampaignEmailMessageJsonMarshaller();
        }
        return instance;
    }
}
