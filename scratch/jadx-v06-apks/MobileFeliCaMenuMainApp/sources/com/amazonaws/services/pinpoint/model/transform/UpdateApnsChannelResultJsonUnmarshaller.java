package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.UpdateApnsChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class UpdateApnsChannelResultJsonUnmarshaller implements Unmarshaller<UpdateApnsChannelResult, JsonUnmarshallerContext> {
    private static UpdateApnsChannelResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public UpdateApnsChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new UpdateApnsChannelResult();
    }

    public static UpdateApnsChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateApnsChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
