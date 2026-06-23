package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.MessageRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class MessageRequestJsonUnmarshaller implements Unmarshaller<MessageRequest, JsonUnmarshallerContext> {
    private static MessageRequestJsonUnmarshaller instance;

    MessageRequestJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public MessageRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        MessageRequest messageRequest = new MessageRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Addresses")) {
                messageRequest.setAddresses(new MapUnmarshaller(AddressConfigurationJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Context")) {
                messageRequest.setContext(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Endpoints")) {
                messageRequest.setEndpoints(new MapUnmarshaller(EndpointSendConfigurationJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("MessageConfiguration")) {
                messageRequest.setMessageConfiguration(DirectMessageConfigurationJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("TraceId")) {
                messageRequest.setTraceId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return messageRequest;
    }

    public static MessageRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new MessageRequestJsonUnmarshaller();
        }
        return instance;
    }
}
