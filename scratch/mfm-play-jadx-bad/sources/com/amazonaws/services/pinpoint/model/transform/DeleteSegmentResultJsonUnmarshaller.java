package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DeleteSegmentResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class DeleteSegmentResultJsonUnmarshaller implements Unmarshaller<DeleteSegmentResult, JsonUnmarshallerContext> {
    private static DeleteSegmentResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public DeleteSegmentResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new DeleteSegmentResult();
    }

    public static DeleteSegmentResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteSegmentResultJsonUnmarshaller();
        }
        return instance;
    }
}
