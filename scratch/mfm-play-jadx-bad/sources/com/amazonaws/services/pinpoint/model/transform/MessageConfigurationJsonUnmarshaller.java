package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.MessageConfiguration;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class MessageConfigurationJsonUnmarshaller implements Unmarshaller<MessageConfiguration, JsonUnmarshallerContext> {
    private static MessageConfigurationJsonUnmarshaller instance;

    MessageConfigurationJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public MessageConfiguration unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        MessageConfiguration messageConfiguration = new MessageConfiguration();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("ADMMessage")) {
                messageConfiguration.setADMMessage(MessageJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("APNSMessage")) {
                messageConfiguration.setAPNSMessage(MessageJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("BaiduMessage")) {
                messageConfiguration.setBaiduMessage(MessageJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("DefaultMessage")) {
                messageConfiguration.setDefaultMessage(MessageJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("EmailMessage")) {
                messageConfiguration.setEmailMessage(CampaignEmailMessageJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("GCMMessage")) {
                messageConfiguration.setGCMMessage(MessageJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("SMSMessage")) {
                messageConfiguration.setSMSMessage(CampaignSmsMessageJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return messageConfiguration;
    }

    public static MessageConfigurationJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new MessageConfigurationJsonUnmarshaller();
        }
        return instance;
    }
}
