package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GetChannelsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class GetChannelsResultJsonUnmarshaller implements Unmarshaller<GetChannelsResult, JsonUnmarshallerContext> {
    private static GetChannelsResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GetChannelsResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new GetChannelsResult();
    }

    public static GetChannelsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GetChannelsResultJsonUnmarshaller();
        }
        return instance;
    }
}
