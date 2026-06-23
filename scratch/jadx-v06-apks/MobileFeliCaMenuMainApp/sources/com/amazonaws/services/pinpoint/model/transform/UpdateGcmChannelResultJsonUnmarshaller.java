package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.UpdateGcmChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class UpdateGcmChannelResultJsonUnmarshaller implements Unmarshaller<UpdateGcmChannelResult, JsonUnmarshallerContext> {
    private static UpdateGcmChannelResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public UpdateGcmChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new UpdateGcmChannelResult();
    }

    public static UpdateGcmChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateGcmChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
