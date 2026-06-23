package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CampaignSmsMessage;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class CampaignSmsMessageJsonMarshaller {
    private static CampaignSmsMessageJsonMarshaller instance;

    CampaignSmsMessageJsonMarshaller() {
    }

    public void marshall(CampaignSmsMessage campaignSmsMessage, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (campaignSmsMessage.getBody() != null) {
            String body = campaignSmsMessage.getBody();
            awsJsonWriter.name("Body");
            awsJsonWriter.value(body);
        }
        if (campaignSmsMessage.getMessageType() != null) {
            String messageType = campaignSmsMessage.getMessageType();
            awsJsonWriter.name("MessageType");
            awsJsonWriter.value(messageType);
        }
        if (campaignSmsMessage.getSenderId() != null) {
            String senderId = campaignSmsMessage.getSenderId();
            awsJsonWriter.name("SenderId");
            awsJsonWriter.value(senderId);
        }
        awsJsonWriter.endObject();
    }

    public static CampaignSmsMessageJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new CampaignSmsMessageJsonMarshaller();
        }
        return instance;
    }
}
