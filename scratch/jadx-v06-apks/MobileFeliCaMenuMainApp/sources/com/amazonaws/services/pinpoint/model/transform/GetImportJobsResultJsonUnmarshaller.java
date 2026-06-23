package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetImportJobsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetImportJobsResultJsonUnmarshaller implements Unmarshaller<GetImportJobsResult, JsonUnmarshallerContext> {
    private static GetImportJobsResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public GetImportJobsResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetImportJobsResult();
    }

    public static GetImportJobsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetImportJobsResultJsonUnmarshaller();
        }
        return instance;
    }
}
