package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SendUsersMessageResponse;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class SendUsersMessageResponseJsonUnmarshaller implements Unmarshaller<SendUsersMessageResponse, JsonUnmarshallerContext> {
    private static SendUsersMessageResponseJsonUnmarshaller instance;

    SendUsersMessageResponseJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public SendUsersMessageResponse unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SendUsersMessageResponse sendUsersMessageResponse = new SendUsersMessageResponse();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("ApplicationId")) {
                sendUsersMessageResponse.setApplicationId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("RequestId")) {
                sendUsersMessageResponse.setRequestId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Result")) {
                sendUsersMessageResponse.setResult(new MapUnmarshaller(new MapUnmarshaller(EndpointMessageResultJsonUnmarshaller.getInstance())).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return sendUsersMessageResponse;
    }

    public static SendUsersMessageResponseJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SendUsersMessageResponseJsonUnmarshaller();
        }
        return instance;
    }
}
