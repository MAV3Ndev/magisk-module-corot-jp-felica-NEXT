package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DirectMessageConfiguration;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class DirectMessageConfigurationJsonUnmarshaller implements Unmarshaller<DirectMessageConfiguration, JsonUnmarshallerContext> {
    private static DirectMessageConfigurationJsonUnmarshaller instance;

    DirectMessageConfigurationJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public DirectMessageConfiguration unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        DirectMessageConfiguration directMessageConfiguration = new DirectMessageConfiguration();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("ADMMessage")) {
                directMessageConfiguration.setADMMessage(ADMMessageJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("APNSMessage")) {
                directMessageConfiguration.setAPNSMessage(APNSMessageJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("BaiduMessage")) {
                directMessageConfiguration.setBaiduMessage(BaiduMessageJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("DefaultMessage")) {
                directMessageConfiguration.setDefaultMessage(DefaultMessageJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("DefaultPushNotificationMessage")) {
                directMessageConfiguration.setDefaultPushNotificationMessage(DefaultPushNotificationMessageJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("EmailMessage")) {
                directMessageConfiguration.setEmailMessage(EmailMessageJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("GCMMessage")) {
                directMessageConfiguration.setGCMMessage(GCMMessageJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("SMSMessage")) {
                directMessageConfiguration.setSMSMessage(SMSMessageJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("VoiceMessage")) {
                directMessageConfiguration.setVoiceMessage(VoiceMessageJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return directMessageConfiguration;
    }

    public static DirectMessageConfigurationJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DirectMessageConfigurationJsonUnmarshaller();
        }
        return instance;
    }
}
