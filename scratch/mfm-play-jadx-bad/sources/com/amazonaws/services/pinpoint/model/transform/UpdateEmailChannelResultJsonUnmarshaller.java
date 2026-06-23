package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.UpdateEmailChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class UpdateEmailChannelResultJsonUnmarshaller implements Unmarshaller<UpdateEmailChannelResult, JsonUnmarshallerContext> {
    private static UpdateEmailChannelResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public UpdateEmailChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new UpdateEmailChannelResult();
    }

    public static UpdateEmailChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateEmailChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
