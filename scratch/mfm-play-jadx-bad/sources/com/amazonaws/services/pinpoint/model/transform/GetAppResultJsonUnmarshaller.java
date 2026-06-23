package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetAppResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetAppResultJsonUnmarshaller implements Unmarshaller<GetAppResult, JsonUnmarshallerContext> {
    private static GetAppResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetAppResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetAppResult();
    }

    public static GetAppResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetAppResultJsonUnmarshaller();
        }
        return instance;
    }
}
