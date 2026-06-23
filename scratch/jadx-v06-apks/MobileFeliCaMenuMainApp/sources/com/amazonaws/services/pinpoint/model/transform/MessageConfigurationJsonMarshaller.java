package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CampaignEmailMessage;
import com.amazonaws.services.pinpoint.model.CampaignSmsMessage;
import com.amazonaws.services.pinpoint.model.Message;
import com.amazonaws.services.pinpoint.model.MessageConfiguration;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class MessageConfigurationJsonMarshaller {
    private static MessageConfigurationJsonMarshaller instance;

    MessageConfigurationJsonMarshaller() {
    }

    public void marshall(MessageConfiguration messageConfiguration, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (messageConfiguration.getADMMessage() != null) {
            Message aDMMessage = messageConfiguration.getADMMessage();
            awsJsonWriter.name("ADMMessage");
            MessageJsonMarshaller.getInstance().marshall(aDMMessage, awsJsonWriter);
        }
        if (messageConfiguration.getAPNSMessage() != null) {
            Message aPNSMessage = messageConfiguration.getAPNSMessage();
            awsJsonWriter.name("APNSMessage");
            MessageJsonMarshaller.getInstance().marshall(aPNSMessage, awsJsonWriter);
        }
        if (messageConfiguration.getBaiduMessage() != null) {
            Message baiduMessage = messageConfiguration.getBaiduMessage();
            awsJsonWriter.name("BaiduMessage");
            MessageJsonMarshaller.getInstance().marshall(baiduMessage, awsJsonWriter);
        }
        if (messageConfiguration.getDefaultMessage() != null) {
            Message defaultMessage = messageConfiguration.getDefaultMessage();
            awsJsonWriter.name("DefaultMessage");
            MessageJsonMarshaller.getInstance().marshall(defaultMessage, awsJsonWriter);
        }
        if (messageConfiguration.getEmailMessage() != null) {
            CampaignEmailMessage emailMessage = messageConfiguration.getEmailMessage();
            awsJsonWriter.name("EmailMessage");
            CampaignEmailMessageJsonMarshaller.getInstance().marshall(emailMessage, awsJsonWriter);
        }
        if (messageConfiguration.getGCMMessage() != null) {
            Message gCMMessage = messageConfiguration.getGCMMessage();
            awsJsonWriter.name("GCMMessage");
            MessageJsonMarshaller.getInstance().marshall(gCMMessage, awsJsonWriter);
        }
        if (messageConfiguration.getSMSMessage() != null) {
            CampaignSmsMessage sMSMessage = messageConfiguration.getSMSMessage();
            awsJsonWriter.name("SMSMessage");
            CampaignSmsMessageJsonMarshaller.getInstance().marshall(sMSMessage, awsJsonWriter);
        }
        awsJsonWriter.endObject();
    }

    public static MessageConfigurationJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new MessageConfigurationJsonMarshaller();
        }
        return instance;
    }
}
