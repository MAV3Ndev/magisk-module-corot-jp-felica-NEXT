package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetExportJobsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetExportJobsResultJsonUnmarshaller implements Unmarshaller<GetExportJobsResult, JsonUnmarshallerContext> {
    private static GetExportJobsResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public GetExportJobsResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetExportJobsResult();
    }

    public static GetExportJobsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetExportJobsResultJsonUnmarshaller();
        }
        return instance;
    }
}
