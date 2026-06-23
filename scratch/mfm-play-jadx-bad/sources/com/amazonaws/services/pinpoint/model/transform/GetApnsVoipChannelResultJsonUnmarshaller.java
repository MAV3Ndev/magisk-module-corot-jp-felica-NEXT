package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetApnsVoipChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetApnsVoipChannelResultJsonUnmarshaller implements Unmarshaller<GetApnsVoipChannelResult, JsonUnmarshallerContext> {
    private static GetApnsVoipChannelResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetApnsVoipChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetApnsVoipChannelResult();
    }

    public static GetApnsVoipChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetApnsVoipChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
