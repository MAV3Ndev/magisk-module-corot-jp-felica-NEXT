package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CreateImportJobResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class CreateImportJobResultJsonUnmarshaller implements Unmarshaller<CreateImportJobResult, JsonUnmarshallerContext> {
    private static CreateImportJobResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public CreateImportJobResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new CreateImportJobResult();
    }

    public static CreateImportJobResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CreateImportJobResultJsonUnmarshaller();
        }
        return instance;
    }
}
