package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.UpdateEndpointsBatchResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class UpdateEndpointsBatchResultJsonUnmarshaller implements Unmarshaller<UpdateEndpointsBatchResult, JsonUnmarshallerContext> {
    private static UpdateEndpointsBatchResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public UpdateEndpointsBatchResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new UpdateEndpointsBatchResult();
    }

    public static UpdateEndpointsBatchResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateEndpointsBatchResultJsonUnmarshaller();
        }
        return instance;
    }
}
