package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EndpointsResponse;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class EndpointsResponseJsonUnmarshaller implements Unmarshaller<EndpointsResponse, JsonUnmarshallerContext> {
    private static EndpointsResponseJsonUnmarshaller instance;

    EndpointsResponseJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public EndpointsResponse unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        EndpointsResponse endpointsResponse = new EndpointsResponse();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("Item")) {
                endpointsResponse.setItem(new ListUnmarshaller(EndpointResponseJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return endpointsResponse;
    }

    public static EndpointsResponseJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new EndpointsResponseJsonUnmarshaller();
        }
        return instance;
    }
}
