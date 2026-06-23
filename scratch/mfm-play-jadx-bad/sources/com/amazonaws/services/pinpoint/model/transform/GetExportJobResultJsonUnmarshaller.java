package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetExportJobResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetExportJobResultJsonUnmarshaller implements Unmarshaller<GetExportJobResult, JsonUnmarshallerContext> {
    private static GetExportJobResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetExportJobResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetExportJobResult();
    }

    public static GetExportJobResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetExportJobResultJsonUnmarshaller();
        }
        return instance;
    }
}
