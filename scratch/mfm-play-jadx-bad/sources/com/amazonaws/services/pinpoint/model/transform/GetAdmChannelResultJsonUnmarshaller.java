package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetAdmChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetAdmChannelResultJsonUnmarshaller implements Unmarshaller<GetAdmChannelResult, JsonUnmarshallerContext> {
    private static GetAdmChannelResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetAdmChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetAdmChannelResult();
    }

    public static GetAdmChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetAdmChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
