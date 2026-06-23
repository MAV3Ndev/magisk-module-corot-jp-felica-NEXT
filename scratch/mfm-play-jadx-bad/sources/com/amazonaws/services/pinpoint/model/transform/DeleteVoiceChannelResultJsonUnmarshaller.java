package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DeleteVoiceChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class DeleteVoiceChannelResultJsonUnmarshaller implements Unmarshaller<DeleteVoiceChannelResult, JsonUnmarshallerContext> {
    private static DeleteVoiceChannelResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public DeleteVoiceChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new DeleteVoiceChannelResult();
    }

    public static DeleteVoiceChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteVoiceChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
