package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.DeleteAppResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class DeleteAppResultJsonUnmarshaller implements Unmarshaller<DeleteAppResult, JsonUnmarshallerContext> {
    private static DeleteAppResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public DeleteAppResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new DeleteAppResult();
    }

    public static DeleteAppResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DeleteAppResultJsonUnmarshaller();
        }
        return instance;
    }
}
