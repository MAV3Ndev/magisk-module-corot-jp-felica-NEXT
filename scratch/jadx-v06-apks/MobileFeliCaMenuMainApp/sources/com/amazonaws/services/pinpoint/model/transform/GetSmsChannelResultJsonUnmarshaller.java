package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetSmsChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetSmsChannelResultJsonUnmarshaller implements Unmarshaller<GetSmsChannelResult, JsonUnmarshallerContext> {
    private static GetSmsChannelResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public GetSmsChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetSmsChannelResult();
    }

    public static GetSmsChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetSmsChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
