package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EndpointMessageResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class EndpointMessageResultJsonUnmarshaller implements Unmarshaller<EndpointMessageResult, JsonUnmarshallerContext> {
    private static EndpointMessageResultJsonUnmarshaller instance;

    EndpointMessageResultJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public EndpointMessageResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        EndpointMessageResult endpointMessageResult = new EndpointMessageResult();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Address")) {
                endpointMessageResult.setAddress(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("DeliveryStatus")) {
                endpointMessageResult.setDeliveryStatus(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("MessageId")) {
                endpointMessageResult.setMessageId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("StatusCode")) {
                endpointMessageResult.setStatusCode(SimpleTypeJsonUnmarshallers.IntegerJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("StatusMessage")) {
                endpointMessageResult.setStatusMessage(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("UpdatedToken")) {
                endpointMessageResult.setUpdatedToken(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return endpointMessageResult;
    }

    public static EndpointMessageResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new EndpointMessageResultJsonUnmarshaller();
        }
        return instance;
    }
}
