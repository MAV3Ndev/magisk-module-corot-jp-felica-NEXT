package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.UpdateAdmChannelResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class UpdateAdmChannelResultJsonUnmarshaller implements Unmarshaller<UpdateAdmChannelResult, JsonUnmarshallerContext> {
    private static UpdateAdmChannelResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public UpdateAdmChannelResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new UpdateAdmChannelResult();
    }

    public static UpdateAdmChannelResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateAdmChannelResultJsonUnmarshaller();
        }
        return instance;
    }
}
