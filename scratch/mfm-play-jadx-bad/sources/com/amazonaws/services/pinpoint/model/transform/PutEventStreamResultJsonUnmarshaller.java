package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.PutEventStreamResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class PutEventStreamResultJsonUnmarshaller implements Unmarshaller<PutEventStreamResult, JsonUnmarshallerContext> {
    private static PutEventStreamResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public PutEventStreamResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new PutEventStreamResult();
    }

    public static PutEventStreamResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new PutEventStreamResultJsonUnmarshaller();
        }
        return instance;
    }
}
