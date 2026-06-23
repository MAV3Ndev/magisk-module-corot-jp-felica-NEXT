package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DeleteApnsChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class DeleteApnsChannelResultJsonUnmarshaller implements Unmarshaller<DeleteApnsChannelResult, JsonUnmarshallerContext> {
    private static DeleteApnsChannelResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public DeleteApnsChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new DeleteApnsChannelResult();
    }

    public static DeleteApnsChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteApnsChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
