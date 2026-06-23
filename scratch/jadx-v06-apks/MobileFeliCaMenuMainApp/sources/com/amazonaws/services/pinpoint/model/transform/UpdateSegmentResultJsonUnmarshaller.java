package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.UpdateSegmentResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class UpdateSegmentResultJsonUnmarshaller implements Unmarshaller<UpdateSegmentResult, JsonUnmarshallerContext> {
    private static UpdateSegmentResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public UpdateSegmentResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new UpdateSegmentResult();
    }

    public static UpdateSegmentResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateSegmentResultJsonUnmarshaller();
        }
        return instance;
    }
}
