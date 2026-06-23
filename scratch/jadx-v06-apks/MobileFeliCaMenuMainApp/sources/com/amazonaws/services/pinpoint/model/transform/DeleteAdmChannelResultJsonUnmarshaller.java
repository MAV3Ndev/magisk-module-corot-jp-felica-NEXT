package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DeleteAdmChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class DeleteAdmChannelResultJsonUnmarshaller implements Unmarshaller<DeleteAdmChannelResult, JsonUnmarshallerContext> {
    private static DeleteAdmChannelResultJsonUnmarshaller instance;

    @Override // com.amazonaws.transform.Unmarshaller
    public DeleteAdmChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new DeleteAdmChannelResult();
    }

    public static DeleteAdmChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteAdmChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
