package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DeleteEmailChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class DeleteEmailChannelResultJsonUnmarshaller implements Unmarshaller<DeleteEmailChannelResult, JsonUnmarshallerContext> {
    private static DeleteEmailChannelResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public DeleteEmailChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new DeleteEmailChannelResult();
    }

    public static DeleteEmailChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteEmailChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
