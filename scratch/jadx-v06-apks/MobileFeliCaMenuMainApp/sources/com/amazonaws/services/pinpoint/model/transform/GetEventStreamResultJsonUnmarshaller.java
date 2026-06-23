package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetEventStreamResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetEventStreamResultJsonUnmarshaller implements Unmarshaller<GetEventStreamResult, JsonUnmarshallerContext> {
    private static GetEventStreamResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public GetEventStreamResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetEventStreamResult();
    }

    public static GetEventStreamResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetEventStreamResultJsonUnmarshaller();
        }
        return instance;
    }
}
