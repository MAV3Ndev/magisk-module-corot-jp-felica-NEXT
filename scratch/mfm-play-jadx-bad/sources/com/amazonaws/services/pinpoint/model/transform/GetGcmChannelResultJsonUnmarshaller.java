package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetGcmChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetGcmChannelResultJsonUnmarshaller implements Unmarshaller<GetGcmChannelResult, JsonUnmarshallerContext> {
    private static GetGcmChannelResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetGcmChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetGcmChannelResult();
    }

    public static GetGcmChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetGcmChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
