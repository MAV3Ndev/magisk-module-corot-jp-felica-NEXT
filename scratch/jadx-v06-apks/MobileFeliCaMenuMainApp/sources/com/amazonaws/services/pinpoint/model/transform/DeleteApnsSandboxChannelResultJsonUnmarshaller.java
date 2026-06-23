package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DeleteApnsSandboxChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class DeleteApnsSandboxChannelResultJsonUnmarshaller implements Unmarshaller<DeleteApnsSandboxChannelResult, JsonUnmarshallerContext> {
    private static DeleteApnsSandboxChannelResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public DeleteApnsSandboxChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new DeleteApnsSandboxChannelResult();
    }

    public static DeleteApnsSandboxChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteApnsSandboxChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
