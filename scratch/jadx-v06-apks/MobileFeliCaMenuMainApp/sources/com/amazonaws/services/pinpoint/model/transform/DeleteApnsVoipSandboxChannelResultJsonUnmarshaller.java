package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DeleteApnsVoipSandboxChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class DeleteApnsVoipSandboxChannelResultJsonUnmarshaller implements Unmarshaller<DeleteApnsVoipSandboxChannelResult, JsonUnmarshallerContext> {
    private static DeleteApnsVoipSandboxChannelResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public DeleteApnsVoipSandboxChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new DeleteApnsVoipSandboxChannelResult();
    }

    public static DeleteApnsVoipSandboxChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteApnsVoipSandboxChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
