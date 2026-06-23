package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.MessageResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class MessageResultJsonUnmarshaller implements Unmarshaller<MessageResult, JsonUnmarshallerContext> {
    private static MessageResultJsonUnmarshaller instance;

    MessageResultJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public MessageResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        MessageResult messageResult = new MessageResult();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("DeliveryStatus")) {
                messageResult.setDeliveryStatus(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("MessageId")) {
                messageResult.setMessageId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("StatusCode")) {
                messageResult.setStatusCode(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("StatusMessage")) {
                messageResult.setStatusMessage(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("UpdatedToken")) {
                messageResult.setUpdatedToken(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return messageResult;
    }

    public static MessageResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new MessageResultJsonUnmarshaller();
        }
        return instance;
    }
}
