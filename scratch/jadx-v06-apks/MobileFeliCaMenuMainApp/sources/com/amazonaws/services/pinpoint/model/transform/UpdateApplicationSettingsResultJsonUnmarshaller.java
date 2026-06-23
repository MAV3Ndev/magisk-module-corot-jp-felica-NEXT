package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.UpdateApplicationSettingsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class UpdateApplicationSettingsResultJsonUnmarshaller implements Unmarshaller<UpdateApplicationSettingsResult, JsonUnmarshallerContext> {
    private static UpdateApplicationSettingsResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public UpdateApplicationSettingsResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new UpdateApplicationSettingsResult();
    }

    public static UpdateApplicationSettingsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateApplicationSettingsResultJsonUnmarshaller();
        }
        return instance;
    }
}
