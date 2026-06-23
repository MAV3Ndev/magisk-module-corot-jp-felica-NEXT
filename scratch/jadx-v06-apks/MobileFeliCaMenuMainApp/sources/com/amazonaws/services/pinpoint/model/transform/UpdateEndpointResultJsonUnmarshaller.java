package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.UpdateEndpointResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class UpdateEndpointResultJsonUnmarshaller implements Unmarshaller<UpdateEndpointResult, JsonUnmarshallerContext> {
    private static UpdateEndpointResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public UpdateEndpointResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new UpdateEndpointResult();
    }

    public static UpdateEndpointResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateEndpointResultJsonUnmarshaller();
        }
        return instance;
    }
}
