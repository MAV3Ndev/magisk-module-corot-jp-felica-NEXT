package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DeleteGcmChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class DeleteGcmChannelResultJsonUnmarshaller implements Unmarshaller<DeleteGcmChannelResult, JsonUnmarshallerContext> {
    private static DeleteGcmChannelResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public DeleteGcmChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new DeleteGcmChannelResult();
    }

    public static DeleteGcmChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteGcmChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
