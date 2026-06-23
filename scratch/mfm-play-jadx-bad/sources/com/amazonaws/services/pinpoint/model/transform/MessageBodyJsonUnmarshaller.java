package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.MessageBody;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class MessageBodyJsonUnmarshaller implements Unmarshaller<MessageBody, JsonUnmarshallerContext> {
    private static MessageBodyJsonUnmarshaller instance;

    MessageBodyJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public MessageBody unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        MessageBody messageBody = new MessageBody();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Message")) {
                messageBody.setMessage(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("RequestID")) {
                messageBody.setRequestID(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return messageBody;
    }

    public static MessageBodyJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new MessageBodyJsonUnmarshaller();
        }
        return instance;
    }
}
