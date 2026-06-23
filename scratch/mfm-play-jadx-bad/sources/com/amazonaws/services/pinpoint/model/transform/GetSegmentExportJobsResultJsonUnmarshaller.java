package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetSegmentExportJobsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetSegmentExportJobsResultJsonUnmarshaller implements Unmarshaller<GetSegmentExportJobsResult, JsonUnmarshallerContext> {
    private static GetSegmentExportJobsResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetSegmentExportJobsResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetSegmentExportJobsResult();
    }

    public static GetSegmentExportJobsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetSegmentExportJobsResultJsonUnmarshaller();
        }
        return instance;
    }
}
