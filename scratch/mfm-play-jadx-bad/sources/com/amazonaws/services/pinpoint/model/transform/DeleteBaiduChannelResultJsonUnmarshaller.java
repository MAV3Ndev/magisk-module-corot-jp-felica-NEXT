package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DeleteBaiduChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class DeleteBaiduChannelResultJsonUnmarshaller implements Unmarshaller<DeleteBaiduChannelResult, JsonUnmarshallerContext> {
    private static DeleteBaiduChannelResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public DeleteBaiduChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new DeleteBaiduChannelResult();
    }

    public static DeleteBaiduChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteBaiduChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
