package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetImportJobResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetImportJobResultJsonUnmarshaller implements Unmarshaller<GetImportJobResult, JsonUnmarshallerContext> {
    private static GetImportJobResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public GetImportJobResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetImportJobResult();
    }

    public static GetImportJobResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetImportJobResultJsonUnmarshaller();
        }
        return instance;
    }
}
