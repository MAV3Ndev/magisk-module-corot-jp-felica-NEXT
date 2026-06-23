package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DeleteApnsVoipChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class DeleteApnsVoipChannelResultJsonUnmarshaller implements Unmarshaller<DeleteApnsVoipChannelResult, JsonUnmarshallerContext> {
    private static DeleteApnsVoipChannelResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public DeleteApnsVoipChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new DeleteApnsVoipChannelResult();
    }

    public static DeleteApnsVoipChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteApnsVoipChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
