package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetBaiduChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetBaiduChannelResultJsonUnmarshaller implements Unmarshaller<GetBaiduChannelResult, JsonUnmarshallerContext> {
    private static GetBaiduChannelResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetBaiduChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetBaiduChannelResult();
    }

    public static GetBaiduChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetBaiduChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
