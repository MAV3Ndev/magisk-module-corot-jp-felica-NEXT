package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetVoiceChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetVoiceChannelResultJsonUnmarshaller implements Unmarshaller<GetVoiceChannelResult, JsonUnmarshallerContext> {
    private static GetVoiceChannelResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetVoiceChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetVoiceChannelResult();
    }

    public static GetVoiceChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetVoiceChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
