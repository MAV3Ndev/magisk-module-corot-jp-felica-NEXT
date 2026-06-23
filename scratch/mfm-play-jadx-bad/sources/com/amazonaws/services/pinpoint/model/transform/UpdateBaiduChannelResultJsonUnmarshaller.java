package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.UpdateBaiduChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class UpdateBaiduChannelResultJsonUnmarshaller implements Unmarshaller<UpdateBaiduChannelResult, JsonUnmarshallerContext> {
    private static UpdateBaiduChannelResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public UpdateBaiduChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new UpdateBaiduChannelResult();
    }

    public static UpdateBaiduChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateBaiduChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
