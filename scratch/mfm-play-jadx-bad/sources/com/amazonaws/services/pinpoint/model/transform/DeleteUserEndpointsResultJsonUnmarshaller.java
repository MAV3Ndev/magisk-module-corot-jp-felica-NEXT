package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DeleteUserEndpointsResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class DeleteUserEndpointsResultJsonUnmarshaller implements Unmarshaller<DeleteUserEndpointsResult, JsonUnmarshallerContext> {
    private static DeleteUserEndpointsResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public DeleteUserEndpointsResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new DeleteUserEndpointsResult();
    }

    public static DeleteUserEndpointsResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteUserEndpointsResultJsonUnmarshaller();
        }
        return instance;
    }
}
