package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.UpdateApnsVoipChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class UpdateApnsVoipChannelResultJsonUnmarshaller implements Unmarshaller<UpdateApnsVoipChannelResult, JsonUnmarshallerContext> {
    private static UpdateApnsVoipChannelResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public UpdateApnsVoipChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new UpdateApnsVoipChannelResult();
    }

    public static UpdateApnsVoipChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateApnsVoipChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
