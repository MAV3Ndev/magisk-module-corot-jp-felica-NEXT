package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DeleteSmsChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class DeleteSmsChannelResultJsonUnmarshaller implements Unmarshaller<DeleteSmsChannelResult, JsonUnmarshallerContext> {
    private static DeleteSmsChannelResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public DeleteSmsChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new DeleteSmsChannelResult();
    }

    public static DeleteSmsChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteSmsChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
