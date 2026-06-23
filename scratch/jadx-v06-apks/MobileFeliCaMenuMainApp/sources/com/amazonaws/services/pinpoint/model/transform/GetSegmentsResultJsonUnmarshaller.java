package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetSegmentsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentsResultJsonUnmarshaller implements Unmarshaller<GetSegmentsResult, JsonUnmarshallerContext> {
    private static GetSegmentsResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public GetSegmentsResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetSegmentsResult();
    }

    public static GetSegmentsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetSegmentsResultJsonUnmarshaller();
        }
        return instance;
    }
}
