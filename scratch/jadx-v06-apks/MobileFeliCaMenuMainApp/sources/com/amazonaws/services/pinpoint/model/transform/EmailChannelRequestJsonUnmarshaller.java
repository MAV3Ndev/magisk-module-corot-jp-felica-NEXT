package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EmailChannelRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class EmailChannelRequestJsonUnmarshaller implements Unmarshaller<EmailChannelRequest, JsonUnmarshallerContext> {
    private static EmailChannelRequestJsonUnmarshaller instance;

    EmailChannelRequestJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public EmailChannelRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        EmailChannelRequest emailChannelRequest = new EmailChannelRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("ConfigurationSet")) {
                emailChannelRequest.setConfigurationSet(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Enabled")) {
                emailChannelRequest.setEnabled(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("FromAddress")) {
                emailChannelRequest.setFromAddress(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Identity")) {
                emailChannelRequest.setIdentity(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("RoleArn")) {
                emailChannelRequest.setRoleArn(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return emailChannelRequest;
    }

    public static EmailChannelRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new EmailChannelRequestJsonUnmarshaller();
        }
        return instance;
    }
}
