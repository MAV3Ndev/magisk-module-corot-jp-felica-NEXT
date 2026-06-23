package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DeleteEndpointResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class DeleteEndpointResultJsonUnmarshaller implements Unmarshaller<DeleteEndpointResult, JsonUnmarshallerContext> {
    private static DeleteEndpointResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public DeleteEndpointResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new DeleteEndpointResult();
    }

    public static DeleteEndpointResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteEndpointResultJsonUnmarshaller();
        }
        return instance;
    }
}
