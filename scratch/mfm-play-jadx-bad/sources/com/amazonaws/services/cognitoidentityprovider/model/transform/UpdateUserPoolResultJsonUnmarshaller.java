package com.amazonaws.services.cognitoidentityprovider.model.transform;

import com.amazonaws.services.cognitoidentityprovider.model.UpdateUserPoolResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;

/* JADX INFO: loaded from: classes.dex */
public class UpdateUserPoolResultJsonUnmarshaller implements Unmarshaller<UpdateUserPoolResult, JsonUnmarshallerContext> {
    private static UpdateUserPoolResultJsonUnmarshaller instance;

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public UpdateUserPoolResult unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        return new UpdateUserPoolResult();
    }

    public static UpdateUserPoolResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateUserPoolResultJsonUnmarshaller();
        }
        return instance;
    }
}
