package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CreateExportJobResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class CreateExportJobResultJsonUnmarshaller implements Unmarshaller<CreateExportJobResult, JsonUnmarshallerContext> {
    private static CreateExportJobResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public CreateExportJobResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new CreateExportJobResult();
    }

    public static CreateExportJobResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CreateExportJobResultJsonUnmarshaller();
        }
        return instance;
    }
}
