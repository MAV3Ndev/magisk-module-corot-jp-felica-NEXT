package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EndpointBatchRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class EndpointBatchRequestJsonUnmarshaller implements Unmarshaller<EndpointBatchRequest, JsonUnmarshallerContext> {
    private static EndpointBatchRequestJsonUnmarshaller instance;

    EndpointBatchRequestJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public EndpointBatchRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        EndpointBatchRequest endpointBatchRequest = new EndpointBatchRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("Item")) {
                endpointBatchRequest.setItem(new ListUnmarshaller(EndpointBatchItemJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return endpointBatchRequest;
    }

    public static EndpointBatchRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new EndpointBatchRequestJsonUnmarshaller();
        }
        return instance;
    }
}
