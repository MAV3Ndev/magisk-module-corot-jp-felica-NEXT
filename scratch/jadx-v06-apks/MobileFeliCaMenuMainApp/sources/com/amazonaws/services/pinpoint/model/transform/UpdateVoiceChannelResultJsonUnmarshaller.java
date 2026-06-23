package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.UpdateVoiceChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class UpdateVoiceChannelResultJsonUnmarshaller implements Unmarshaller<UpdateVoiceChannelResult, JsonUnmarshallerContext> {
    private static UpdateVoiceChannelResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public UpdateVoiceChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new UpdateVoiceChannelResult();
    }

    public static UpdateVoiceChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateVoiceChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
