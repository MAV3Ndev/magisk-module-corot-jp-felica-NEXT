package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.UpdateApnsSandboxChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class UpdateApnsSandboxChannelResultJsonUnmarshaller implements Unmarshaller<UpdateApnsSandboxChannelResult, JsonUnmarshallerContext> {
    private static UpdateApnsSandboxChannelResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public UpdateApnsSandboxChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new UpdateApnsSandboxChannelResult();
    }

    public static UpdateApnsSandboxChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateApnsSandboxChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
