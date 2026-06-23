package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.CreateSegmentResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class CreateSegmentResultJsonUnmarshaller implements Unmarshaller<CreateSegmentResult, JsonUnmarshallerContext> {
    private static CreateSegmentResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public CreateSegmentResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new CreateSegmentResult();
    }

    public static CreateSegmentResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new CreateSegmentResultJsonUnmarshaller();
        }
        return instance;
    }
}
