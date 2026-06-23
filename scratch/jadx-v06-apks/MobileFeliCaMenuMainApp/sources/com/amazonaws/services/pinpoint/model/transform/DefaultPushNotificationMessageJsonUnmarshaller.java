package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.DefaultPushNotificationMessage;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class DefaultPushNotificationMessageJsonUnmarshaller implements Unmarshaller<DefaultPushNotificationMessage, JsonUnmarshallerContext> {
    private static DefaultPushNotificationMessageJsonUnmarshaller instance;

    DefaultPushNotificationMessageJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public DefaultPushNotificationMessage unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        DefaultPushNotificationMessage defaultPushNotificationMessage = new DefaultPushNotificationMessage();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals(JsonDocumentFields.ACTION)) {
                defaultPushNotificationMessage.setAction(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Body")) {
                defaultPushNotificationMessage.setBody(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Data")) {
                defaultPushNotificationMessage.setData(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("SilentPush")) {
                defaultPushNotificationMessage.setSilentPush(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Substitutions")) {
                defaultPushNotificationMessage.setSubstitutions(new MapUnmarshaller(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance())).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Title")) {
                defaultPushNotificationMessage.setTitle(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Url")) {
                defaultPushNotificationMessage.setUrl(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return defaultPushNotificationMessage;
    }

    public static DefaultPushNotificationMessageJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DefaultPushNotificationMessageJsonUnmarshaller();
        }
        return instance;
    }
}
