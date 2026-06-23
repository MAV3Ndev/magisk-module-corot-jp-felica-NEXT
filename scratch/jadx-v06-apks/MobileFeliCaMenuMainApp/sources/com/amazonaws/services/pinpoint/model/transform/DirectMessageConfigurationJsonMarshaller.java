package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ADMMessage;
import com.amazonaws.services.pinpoint.model.APNSMessage;
import com.amazonaws.services.pinpoint.model.BaiduMessage;
import com.amazonaws.services.pinpoint.model.DefaultMessage;
import com.amazonaws.services.pinpoint.model.DefaultPushNotificationMessage;
import com.amazonaws.services.pinpoint.model.DirectMessageConfiguration;
import com.amazonaws.services.pinpoint.model.EmailMessage;
import com.amazonaws.services.pinpoint.model.GCMMessage;
import com.amazonaws.services.pinpoint.model.SMSMessage;
import com.amazonaws.services.pinpoint.model.VoiceMessage;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class DirectMessageConfigurationJsonMarshaller {
    private static DirectMessageConfigurationJsonMarshaller instance;

    DirectMessageConfigurationJsonMarshaller() {
    }

    public void marshall(DirectMessageConfiguration directMessageConfiguration, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (directMessageConfiguration.getADMMessage() != null) {
            ADMMessage aDMMessage = directMessageConfiguration.getADMMessage();
            awsJsonWriter.name("ADMMessage");
            ADMMessageJsonMarshaller.getInstance().marshall(aDMMessage, awsJsonWriter);
        }
        if (directMessageConfiguration.getAPNSMessage() != null) {
            APNSMessage aPNSMessage = directMessageConfiguration.getAPNSMessage();
            awsJsonWriter.name("APNSMessage");
            APNSMessageJsonMarshaller.getInstance().marshall(aPNSMessage, awsJsonWriter);
        }
        if (directMessageConfiguration.getBaiduMessage() != null) {
            BaiduMessage baiduMessage = directMessageConfiguration.getBaiduMessage();
            awsJsonWriter.name("BaiduMessage");
            BaiduMessageJsonMarshaller.getInstance().marshall(baiduMessage, awsJsonWriter);
        }
        if (directMessageConfiguration.getDefaultMessage() != null) {
            DefaultMessage defaultMessage = directMessageConfiguration.getDefaultMessage();
            awsJsonWriter.name("DefaultMessage");
            DefaultMessageJsonMarshaller.getInstance().marshall(defaultMessage, awsJsonWriter);
        }
        if (directMessageConfiguration.getDefaultPushNotificationMessage() != null) {
            DefaultPushNotificationMessage defaultPushNotificationMessage = directMessageConfiguration.getDefaultPushNotificationMessage();
            awsJsonWriter.name("DefaultPushNotificationMessage");
            DefaultPushNotificationMessageJsonMarshaller.getInstance().marshall(defaultPushNotificationMessage, awsJsonWriter);
        }
        if (directMessageConfiguration.getEmailMessage() != null) {
            EmailMessage emailMessage = directMessageConfiguration.getEmailMessage();
            awsJsonWriter.name("EmailMessage");
            EmailMessageJsonMarshaller.getInstance().marshall(emailMessage, awsJsonWriter);
        }
        if (directMessageConfiguration.getGCMMessage() != null) {
            GCMMessage gCMMessage = directMessageConfiguration.getGCMMessage();
            awsJsonWriter.name("GCMMessage");
            GCMMessageJsonMarshaller.getInstance().marshall(gCMMessage, awsJsonWriter);
        }
        if (directMessageConfiguration.getSMSMessage() != null) {
            SMSMessage sMSMessage = directMessageConfiguration.getSMSMessage();
            awsJsonWriter.name("SMSMessage");
            SMSMessageJsonMarshaller.getInstance().marshall(sMSMessage, awsJsonWriter);
        }
        if (directMessageConfiguration.getVoiceMessage() != null) {
            VoiceMessage voiceMessage = directMessageConfiguration.getVoiceMessage();
            awsJsonWriter.name("VoiceMessage");
            VoiceMessageJsonMarshaller.getInstance().marshall(voiceMessage, awsJsonWriter);
        }
        awsJsonWriter.endObject();
    }

    public static DirectMessageConfigurationJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new DirectMessageConfigurationJsonMarshaller();
        }
        return instance;
    }
}
