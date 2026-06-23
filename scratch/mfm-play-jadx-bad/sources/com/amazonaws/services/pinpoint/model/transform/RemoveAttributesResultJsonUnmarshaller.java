package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.RemoveAttributesResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class RemoveAttributesResultJsonUnmarshaller implements Unmarshaller<RemoveAttributesResult, JsonUnmarshallerContext> {
    private static RemoveAttributesResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public RemoveAttributesResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new RemoveAttributesResult();
    }

    public static RemoveAttributesResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new RemoveAttributesResultJsonUnmarshaller();
        }
        return instance;
    }
}
