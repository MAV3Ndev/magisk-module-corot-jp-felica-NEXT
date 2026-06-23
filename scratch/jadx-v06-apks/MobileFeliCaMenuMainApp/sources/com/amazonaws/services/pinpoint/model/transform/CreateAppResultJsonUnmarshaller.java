package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CreateAppResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class CreateAppResultJsonUnmarshaller implements Unmarshaller<CreateAppResult, JsonUnmarshallerContext> {
    private static CreateAppResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public CreateAppResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new CreateAppResult();
    }

    public static CreateAppResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CreateAppResultJsonUnmarshaller();
        }
        return instance;
    }
}
