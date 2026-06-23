package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.UpdateSmsChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class UpdateSmsChannelResultJsonUnmarshaller implements Unmarshaller<UpdateSmsChannelResult, JsonUnmarshallerContext> {
    private static UpdateSmsChannelResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public UpdateSmsChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new UpdateSmsChannelResult();
    }

    public static UpdateSmsChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateSmsChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
