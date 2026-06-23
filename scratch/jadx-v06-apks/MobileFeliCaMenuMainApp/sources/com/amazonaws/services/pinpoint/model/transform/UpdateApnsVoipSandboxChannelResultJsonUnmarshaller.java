package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.UpdateApnsVoipSandboxChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class UpdateApnsVoipSandboxChannelResultJsonUnmarshaller implements Unmarshaller<UpdateApnsVoipSandboxChannelResult, JsonUnmarshallerContext> {
    private static UpdateApnsVoipSandboxChannelResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public UpdateApnsVoipSandboxChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new UpdateApnsVoipSandboxChannelResult();
    }

    public static UpdateApnsVoipSandboxChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateApnsVoipSandboxChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
