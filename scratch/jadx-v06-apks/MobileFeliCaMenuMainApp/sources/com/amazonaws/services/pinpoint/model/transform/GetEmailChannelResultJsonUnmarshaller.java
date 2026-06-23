package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetEmailChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetEmailChannelResultJsonUnmarshaller implements Unmarshaller<GetEmailChannelResult, JsonUnmarshallerContext> {
    private static GetEmailChannelResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public GetEmailChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetEmailChannelResult();
    }

    public static GetEmailChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetEmailChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
