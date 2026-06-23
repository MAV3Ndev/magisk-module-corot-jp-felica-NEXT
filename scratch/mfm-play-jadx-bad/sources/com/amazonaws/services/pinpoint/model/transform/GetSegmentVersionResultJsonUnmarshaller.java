package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetSegmentVersionResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentVersionResultJsonUnmarshaller implements Unmarshaller<GetSegmentVersionResult, JsonUnmarshallerContext> {
    private static GetSegmentVersionResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetSegmentVersionResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetSegmentVersionResult();
    }

    public static GetSegmentVersionResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetSegmentVersionResultJsonUnmarshaller();
        }
        return instance;
    }
}
