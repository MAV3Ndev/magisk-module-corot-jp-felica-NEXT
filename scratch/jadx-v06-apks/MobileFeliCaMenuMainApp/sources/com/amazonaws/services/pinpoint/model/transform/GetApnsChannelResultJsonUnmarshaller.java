package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetApnsChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetApnsChannelResultJsonUnmarshaller implements Unmarshaller<GetApnsChannelResult, JsonUnmarshallerContext> {
    private static GetApnsChannelResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public GetApnsChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetApnsChannelResult();
    }

    public static GetApnsChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetApnsChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
