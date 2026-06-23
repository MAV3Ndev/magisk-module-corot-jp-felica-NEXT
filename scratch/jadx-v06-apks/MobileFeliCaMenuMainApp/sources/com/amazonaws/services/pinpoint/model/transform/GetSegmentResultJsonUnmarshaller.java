package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetSegmentResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentResultJsonUnmarshaller implements Unmarshaller<GetSegmentResult, JsonUnmarshallerContext> {
    private static GetSegmentResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public GetSegmentResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetSegmentResult();
    }

    public static GetSegmentResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetSegmentResultJsonUnmarshaller();
        }
        return instance;
    }
}
