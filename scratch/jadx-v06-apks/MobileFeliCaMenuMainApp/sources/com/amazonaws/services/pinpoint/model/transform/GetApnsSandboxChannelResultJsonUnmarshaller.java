package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetApnsSandboxChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetApnsSandboxChannelResultJsonUnmarshaller implements Unmarshaller<GetApnsSandboxChannelResult, JsonUnmarshallerContext> {
    private static GetApnsSandboxChannelResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public GetApnsSandboxChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetApnsSandboxChannelResult();
    }

    public static GetApnsSandboxChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetApnsSandboxChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
