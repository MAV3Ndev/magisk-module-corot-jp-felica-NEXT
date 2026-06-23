package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetSegmentVersionsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentVersionsResultJsonUnmarshaller implements Unmarshaller<GetSegmentVersionsResult, JsonUnmarshallerContext> {
    private static GetSegmentVersionsResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetSegmentVersionsResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetSegmentVersionsResult();
    }

    public static GetSegmentVersionsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetSegmentVersionsResultJsonUnmarshaller();
        }
        return instance;
    }
}
