package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetAppsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetAppsResultJsonUnmarshaller implements Unmarshaller<GetAppsResult, JsonUnmarshallerContext> {
    private static GetAppsResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetAppsResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetAppsResult();
    }

    public static GetAppsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetAppsResultJsonUnmarshaller();
        }
        return instance;
    }
}
