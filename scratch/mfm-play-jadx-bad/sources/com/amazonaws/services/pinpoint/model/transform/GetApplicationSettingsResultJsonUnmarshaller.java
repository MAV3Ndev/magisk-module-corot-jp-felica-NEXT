package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetApplicationSettingsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetApplicationSettingsResultJsonUnmarshaller implements Unmarshaller<GetApplicationSettingsResult, JsonUnmarshallerContext> {
    private static GetApplicationSettingsResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetApplicationSettingsResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetApplicationSettingsResult();
    }

    public static GetApplicationSettingsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetApplicationSettingsResultJsonUnmarshaller();
        }
        return instance;
    }
}
