package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DeleteEventStreamResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class DeleteEventStreamResultJsonUnmarshaller implements Unmarshaller<DeleteEventStreamResult, JsonUnmarshallerContext> {
    private static DeleteEventStreamResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public DeleteEventStreamResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new DeleteEventStreamResult();
    }

    public static DeleteEventStreamResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteEventStreamResultJsonUnmarshaller();
        }
        return instance;
    }
}
