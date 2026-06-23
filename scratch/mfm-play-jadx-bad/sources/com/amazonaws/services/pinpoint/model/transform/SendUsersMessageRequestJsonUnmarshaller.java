package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SendUsersMessageRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class SendUsersMessageRequestJsonUnmarshaller implements Unmarshaller<SendUsersMessageRequest, JsonUnmarshallerContext> {
    private static SendUsersMessageRequestJsonUnmarshaller instance;

    SendUsersMessageRequestJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public SendUsersMessageRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SendUsersMessageRequest sendUsersMessageRequest = new SendUsersMessageRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Context")) {
                sendUsersMessageRequest.setContext(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("MessageConfiguration")) {
                sendUsersMessageRequest.setMessageConfiguration(DirectMessageConfigurationJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TraceId")) {
                sendUsersMessageRequest.setTraceId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Users")) {
                sendUsersMessageRequest.setUsers(new MapUnmarshaller(EndpointSendConfigurationJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return sendUsersMessageRequest;
    }

    public static SendUsersMessageRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SendUsersMessageRequestJsonUnmarshaller();
        }
        return instance;
    }
}
