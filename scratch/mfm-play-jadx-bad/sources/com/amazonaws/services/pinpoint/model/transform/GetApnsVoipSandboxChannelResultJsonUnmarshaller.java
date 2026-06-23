package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetApnsVoipSandboxChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetApnsVoipSandboxChannelResultJsonUnmarshaller implements Unmarshaller<GetApnsVoipSandboxChannelResult, JsonUnmarshallerContext> {
    private static GetApnsVoipSandboxChannelResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetApnsVoipSandboxChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetApnsVoipSandboxChannelResult();
    }

    public static GetApnsVoipSandboxChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetApnsVoipSandboxChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
