package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetEndpointResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetEndpointResultJsonUnmarshaller implements Unmarshaller<GetEndpointResult, JsonUnmarshallerContext> {
    private static GetEndpointResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetEndpointResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetEndpointResult();
    }

    public static GetEndpointResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetEndpointResultJsonUnmarshaller();
        }
        return instance;
    }
}
